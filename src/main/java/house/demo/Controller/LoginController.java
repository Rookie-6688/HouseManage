package house.demo.Controller;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.google.code.kaptcha.Constants;
import house.demo.Service.HouseService;
import house.demo.Service.LoginService;
import house.demo.bean.BedNumber;
import house.demo.bean.User;
import house.demo.component.AutoLogin;
import house.demo.component.SmsDemo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class LoginController {

    private Map<String,String> codeList=new ConcurrentHashMap();
    private Map<String,Long> timeList=new ConcurrentHashMap<>();
    @Autowired
    LoginService loginService;
    @Autowired
    HouseService houseService;


    @RequestMapping("/")
    public String index(Model model, HttpSession session, HttpServletRequest request) {
        List<BedNumber> bedlist = houseService.getindex();
        model.addAttribute("bedlist", bedlist);
        AutoLogin.AutoLogin(session, request, loginService);
        return "index";
    }

    @RequestMapping("/login")
    public String goLogin() {
        return "login";
    }

    @RequestMapping("/user/login")
    public String login(User user,HttpSession session, Model model,String yanzheng,boolean rememberMe) {
        String sessionCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if(!sessionCode.equals(yanzheng)){
            model.addAttribute("message","验证码错误");
            return "login";
        }
        //获取权限操作对象，利用这个对象来完成登陆操作
        Subject subject = SecurityUtils.getSubject();
        //登陆前清除用户信息，是否如果之前登陆了用户信息会缓存在缓存中，之后每次重新登陆都会登陆成功
        subject.logout();

        //用户是否认证过（是否登陆过），进入if表示没有认证需要认证
        if(!subject.isAuthenticated()){
            //创建用户认证时的身份令牌，并设置我们从页面中传递过来的账号和密码
            UsernamePasswordToken token =new UsernamePasswordToken(user.getUsername(),user.getPassword());
            token.setRememberMe(rememberMe);
            try{
                //指定登陆，会自动调用我们Realm对象中的认证方法，如果登陆失败会抛出各种异常
                subject.login(token);
                return "index";
            }catch (UnknownAccountException e){//用户名不存在
                model.addAttribute("message","用户名错误");
                return "login";
            }catch (IncorrectCredentialsException e){
                model.addAttribute("message","密码错误");
                return "login";
            }
        }
        return "login";
    }

    @RequestMapping(value = "/sendMsg")
    public @ResponseBody
    Map<String, Object> sendMsg(@RequestBody Map<String,Object> requestMap) throws ClientException, InterruptedException {
        String phoneNumber = requestMap.get("phoneNumber").toString();
//        String randomNum = UUID.randomUUID().toString().substring(0, 4);// 生成随机数


        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        long time = c.getTime().getTime();
        timeList.put(phoneNumber,time);

        //发短信
        SendSmsResponse response = SmsDemo.sendSms(phoneNumber);

        Thread.sleep(1000L);
        //查明细
        if (response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = SmsDemo.querySendDetails(response.getBizId(),phoneNumber);
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs()) {
                String[] split = smsSendDetailDTO.getContent().split("：");
                String code = split[1].substring(0, 4);
                System.out.println("验证码" + code);
                codeList.put(phoneNumber,code);
            }
        }
        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put("hash", hash);
        requestMap.put("code",200);
        return resultMap; //将hash值和tamp时间返回给前端
    }

    @RequestMapping(value = "/validateNum", method = RequestMethod.POST)
    public String validateNum(String code,Model model,User user,String yanzheng, HttpServletRequest request) {
        System.out.println(user);
        System.out.println("phone"+"code"+user.getPhone()+"\t"+code);
        long time = (Long)new Date().getTime()!=null?new Date().getTime():0;
        Long phone1 = timeList.get(user.getPhone());
        System.out.println("phone1"+user.getPhone());
        if(phone1<time){
            model.addAttribute("message","验证码超时");
            return "register";
        }
        String realyCode = codeList.get(user.getPhone())!=null?codeList.get(user.getPhone()):"";
        if(!realyCode.equals(code)){
            model.addAttribute("message","验证码错误");
            return "register";
        }
        user.setUid(UUID.randomUUID().toString().substring(0, 8));
        user.setType(0);
        user.setJob("保密");
        user.setSex("保密");
        user.setStar("保密");
        if (loginService.getname(user.getUsername()).size() != 0) {
            model.addAttribute("user", user);
            model.addAttribute("message", "用户名已存在");
            return "register";
        }
        if (loginService.getPhone(user.getPhone()).size() != 0) {
            model.addAttribute("user", user);
            model.addAttribute("message", "手机号已被注册");
            return "register";
        }
        loginService.register(user);
        model.addAttribute("message", "注册成功");
        return "login";
    }


    @RequestMapping("/loginout")
    public String loginout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }
}
