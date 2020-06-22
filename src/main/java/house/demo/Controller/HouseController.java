package house.demo.Controller;

import house.demo.Service.LoginService;
import house.demo.Service.ServiceImpl.HouseServiceImp;
import house.demo.bean.PageModel;
import house.demo.bean.QueryVo;
import house.demo.component.AutoLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class HouseController {

    @Autowired
    HouseServiceImp houseService;
    @Autowired
    LoginService loginService;

    @RequestMapping("/findhouse&roomtype={roomtype}&high={high}&much={much}&livetype={livetype}&order={order}&num={num}")
    public String findhouse(HttpSession session, HttpServletRequest request, @PathVariable String roomtype, @PathVariable String high, @PathVariable String much, @PathVariable String livetype, @PathVariable String num, Model model, @PathVariable String order) {
        AutoLogin.AutoLogin(session, request, loginService);
        QueryVo vo = new QueryVo();
        QueryVo vo2 = new QueryVo();
        vo.setRoomtype(roomtype);
        vo.setMuch(Integer.parseInt(much));
        vo.setHigh(Integer.parseInt(high));
        vo.setLivetype(Integer.parseInt(livetype));
        vo.setOrder(order);
        vo.setCurrentPage(Integer.parseInt(num));                //这里必须新创建一个queryvo对象来传输数据，因为执行getsome方法会改变原queryvo对象里的属性值，及时将代码提前至执行getsome方法之前，也不会改变结果（可能是因为指令重排？）
        vo2.setRoomtype(roomtype);
        vo2.setOrder(order);
        vo2.setMuch(Integer.parseInt(much));
        vo2.setHigh(Integer.parseInt(high));
        vo2.setLivetype(Integer.parseInt(livetype));
        vo2.setCurrentPage(Integer.parseInt(num));
        PageModel pageModel = houseService.getsome(vo);
        model.addAttribute("PageModel", pageModel);
        model.addAttribute("vo", vo2);
        return "search";
    }
}
