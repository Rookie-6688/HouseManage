package house.demo.Controller;

import house.demo.Service.AdminOrderManageService;
import house.demo.Service.BedNumberService;
import house.demo.Service.UserService;
import house.demo.bean.BedNumber;
import house.demo.bean.PageModel;
import house.demo.bean.QueryVo;
import house.demo.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class AdminOrderManageController {

    @Autowired
    AdminOrderManageService adminOrderManageService;
    @Autowired
    BedNumberService bedNumberService;
    @Autowired
    UserService userService;

    @RequestMapping("/adminfindbednumber&state={state}&bid={bid}&location={location}&livetype={livetype}&much={much}&page={page}")
    public String searchbednumber(@PathVariable int state, @PathVariable String bid, @PathVariable String location, @PathVariable Integer livetype, @PathVariable int much, @PathVariable int page, Model model) {
        QueryVo vo = new QueryVo();
        QueryVo vo2 = new QueryVo();
        vo.setState(state);
        vo.setBid(bid);
        vo.setLocation(location);
        vo.setLivetype(livetype);
        vo.setMuch(much);
        vo.setCurrentPage(page);
        vo2.setState(state);
        vo2.setBid(bid);
        vo2.setLocation(location);
        vo2.setLivetype(livetype);
        vo2.setMuch(much);
        PageModel pageModel = adminOrderManageService.searchOrder(vo);
        model.addAttribute("pagemodel", pageModel);
        model.addAttribute("vo", vo2);
        return "admin/order/list";
    }
    //adminfindovertime&state=" + [[${vo.state}]] + "&page=" + [[${pagemodel.currentPageNum}]]
    @RequestMapping("/adminfindovertime&state={state}&page={page}")
    public String adminfindovertime(@PathVariable("state") int state,@PathVariable("page") int page,Model model){
        QueryVo vo=new QueryVo();
        vo.setState(state);
        vo.setCurrentPage(page);
        PageModel pageModel=adminOrderManageService.findOverTime(vo);
        QueryVo vo2=new QueryVo();
        vo2.setState(state);
        model.addAttribute("pagemodel", pageModel);
        model.addAttribute("vo", vo2);
        return "admin/order/list";
    }
    //adminClear&state=
    @RequestMapping("/adminClear&state={state}")
    public String adminClear(@PathVariable("state") int state,Model model){
        adminOrderManageService.clearOver(state);
        model.addAttribute("message", "清除成功");
        return "forward:/adminfindbednumber&state="+state+"&bid=&location=&livetype=&much=-1&page=1";
    }
    @RequestMapping("/orderadmin_edit&bid={bid}&page={page}")
    public String adminedit(@PathVariable String bid, Model model, @PathVariable int page) {
        BedNumber bedNumber = bedNumberService.getbedBybid(bid);
        model.addAttribute("bednumber", bedNumber);
        model.addAttribute("page", page);
        return "admin/order/edit";
    }

    @RequestMapping(value="/adminOrderUpdate")
    public String updateorder(BedNumber bedNumber, String uid, Model model, int page, int prestate) {
        if (bedNumber.getState() != 0) {                //修改的状态不是待出租状态时检查输入的用户ID是否正确
            User user = userService.getUserByid(uid);
            if (user == null) {
                BedNumber bedNumber2 = bedNumberService.getbedBybid(bedNumber.getBid());
                if(bedNumber2.getUser()==null){
                    bedNumber2.setUser(new User());
                }else{
                    bedNumber2.getUser().setUid(uid);                            //将原有的值返回回去
                }
                model.addAttribute("bednumber", bedNumber2);
                model.addAttribute("page", page);
                model.addAttribute("message", "该用户ID不存在，请检查ID是否正确!");
                return "admin/order/edit";
            } else {
                bedNumber.setUser(user);
            }
        } else {
            User user = new User();                //是待出租状态
            user.setUid(null);
            bedNumber.setUser(user);
        }
        if (bedNumber.getState() == 0) {
            bedNumber.setLatedate(null);
        }
        adminOrderManageService.updateOrder(bedNumber);
        model.addAttribute("message", "修改成功");
        return "forward:/adminfindbednumber&state="+prestate+"&bid=&location=&livetype=&much=-1&page="+page;
    }
}
