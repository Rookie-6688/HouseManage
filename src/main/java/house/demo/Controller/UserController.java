package house.demo.Controller;

import house.demo.Service.BedNumberService;
import house.demo.Service.CollectionService;
import house.demo.Service.LoginService;
import house.demo.Service.UserService;
import house.demo.bean.BedNumber;
import house.demo.bean.QueryVo;
import house.demo.bean.User;
import house.demo.component.AutoLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    CollectionService collectionService;
    @Autowired
    LoginService loginService;

    @RequestMapping("/MyCollections")
    public String getCollections(Model model, HttpSession session,HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("collections", collectionService.getCollectionsByuid(user.getUid()));
        return "MySpace/MyCollections";
    }
    @RequestMapping("/MyMessage")
    public String MyMessage(Model model, HttpSession session, HttpServletRequest request) {
        return "MySpace/MyMessage";
    }

    @RequestMapping("/changemsg")
    public String changemsg(User user, String newpassword, HttpSession session, Model model) {
        User user1 = (User) session.getAttribute("user");
        String uid = user1.getUid();
        user.setUid(uid);
        User login = userService.changejudge(user);
        if (login == null) {
            model.addAttribute("msg", "密码错误，修改失败");
            return "MySpace/MyMessageChange";
        }
        QueryVo vo = new QueryVo();
        vo.setUid(user1.getUid());
        vo.setUsername(user.getUsername());
        vo.setPhone(user.getPhone());
        if (userService.checkemail(vo).size() != 0) {
            model.addAttribute("user", user);
            model.addAttribute("message", "邮箱已存在");
            return "MySpace/MyMessageChange";
        }
        if (newpassword != null) {
            user.setPassword(newpassword);
        }
        userService.changeuser(user);
        User user2 = userService.getUserByid(uid);
        session.setAttribute("user", user2);
        model.addAttribute("message", "修改成功");
        return "MySpace/MyMessage";
    }

    @RequestMapping("myorder")
    public String myorder(HttpSession session, Model model,HttpServletRequest request) {
        String uid = ((User) session.getAttribute("user")).getUid();
        List<BedNumber> orderlist = userService.getorder(uid);
        List<BedNumber> paylist = userService.getpay(uid);
        model.addAttribute("orderlist", orderlist);
        model.addAttribute("paylist", paylist);
        return "MySpace/MyOrder";
    }

    @RequestMapping("/concel")
    public @ResponseBody
    void concel(String bid) {
        userService.concel(bid);
    }

    @RequestMapping("/pay")
    public @ResponseBody
    void pay(String bid) {
        userService.pay(bid);
    }

    @RequestMapping("/removeroom")
    public @ResponseBody
    void remove(String bid) {
        userService.removeroom(bid);
    }
}
