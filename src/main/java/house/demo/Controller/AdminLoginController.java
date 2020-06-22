package house.demo.Controller;

import house.demo.Service.AdminUserManageService;
import house.demo.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AdminLoginController {

    @Autowired
    AdminUserManageService adminUserService;

    @RequestMapping("/adminhome")
    public String adminlogin(User user, HttpSession session, Model model) {
        //获取权限操作对象，利用这个对象来完成登陆操作
        Subject subject = SecurityUtils.getSubject();
        System.out.println(user);
        //登陆前清除用户信息，是否如果之前登陆了用户信息会缓存在缓存中，之后每次重新登陆都会登陆成功
        subject.logout();

        //用户是否认证过（是否登陆过），进入if表示没有认证需要认证
        if(!subject.isAuthenticated()){
            //创建用户认证时的身份令牌，并设置我们从页面中传递过来的账号和密码
            UsernamePasswordToken token =new UsernamePasswordToken(user.getUsername(),user.getPassword());
            try{
                //指定登陆，会自动调用我们Realm对象中的认证方法，如果登陆失败会抛出各种异常
                subject.login(token);
                return "admin/home";
            }catch (UnknownAccountException e){//用户名不存在
                model.addAttribute("message","用户名错误");
                return "admin/index";
            }catch (IncorrectCredentialsException e){
                model.addAttribute("message","密码错误");
                return "admin/index";
            }
        }
        return "admin/home";
    }

    @RequestMapping("/adminout")
    public String adminout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/adminlogin";
    }
}
