package house.demo.Controller;

import house.demo.Service.AdminUserManageService;
import house.demo.Service.UserService;
import house.demo.bean.PageModel;
import house.demo.bean.QueryVo;
import house.demo.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class AdminUserManageController {

    @Autowired
    AdminUserManageService adminUserService;
    @Autowired
    UserService userService;

    @RequestMapping("/useradmin_edit&uid={uid}")
    public String goedit(@PathVariable String uid, Model model) {
        User user = userService.getUserByid(uid);
        model.addAttribute("user", user);
        return "/admin/user/edit";
    }

    @RequestMapping("/useradmin_delete&uid={uid}")
    public String removeuser(@PathVariable String uid, Model model) {
        adminUserService.deleuser(uid);
        model.addAttribute("message", "删除成功");
        return "forward:/adminpartfind&uid=&name=&sex=&job=&page=1";
    }

    @RequestMapping("/useradmin_update")
    public String updateuser(User user, Model model) {
        userService.changeuser(user);
        model.addAttribute("message", "修改成功");
        return "forward:/adminpartfind&uid=&name=&sex=&job=&page=1";
    }

    @RequestMapping("/adminaddUser")
    public String goadminaddUser() {
        return "/admin/user/add";
    }

    @RequestMapping("/useradmin_add")
    public String adminuseradd(User user, Model model, HttpSession session) {
        if (adminUserService.getname(user.getUsername()).size() != 0) {
            model.addAttribute("message", "用户名已存在");
            model.addAttribute("user", user);
            return "admin/user/add";
        }
        if (adminUserService.getemail(user.getPhone()).size() != 0) {
            model.addAttribute("message", "手机号已存在");
            model.addAttribute("user", user);
            return "admin/user/add";
        }

        user.setUid(UUID.randomUUID().toString().substring(0, 8));
        adminUserService.adminuseradd(user);
        model.addAttribute("message", "添加成功");
        return "forward:/adminpartfind&uid=&name=&sex=&job=&page=1";
    }

    @RequestMapping("/adminpartfind&uid={uid}&name={name}&sex={sex}&job={job}&page={page}")
    public String findpart(@PathVariable String uid, @PathVariable String name, @PathVariable String sex, @PathVariable String job, @PathVariable int page, Model model) {
        QueryVo vo = new QueryVo();
        QueryVo vo2 = new QueryVo();
        vo.setUid(uid);
        vo.setName(name);
        vo.setSex(sex);
        vo.setJob(job);
        vo.setCurrentPage(page);
        vo2.setUid(uid);
        vo2.setName(name);
        vo2.setSex(sex);
        vo2.setJob(job);
        PageModel pageModel = adminUserService.partusersearch(vo);
        model.addAttribute("pagemodel", pageModel);
        model.addAttribute("vo", vo2);
        return "admin/user/list";
    }


}
