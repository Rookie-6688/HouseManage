package house.demo.component;

import house.demo.Service.LoginService;
import house.demo.bean.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AutoLogin {

    public static void AutoLogin(HttpSession session, HttpServletRequest request, LoginService loginService) {            //自动登陆，使用在用户可以不登录访问的那些页面自动登陆
        Cookie[] cookies = request.getCookies();
        Cookie cookieAuto = CookieUtil.getCookie(cookies, "auto");
        if (cookieAuto != null) {
            if (session.getAttribute("user") == null) {
                Cookie cookieUserName = CookieUtil.getCookie(cookies, "username");
                Cookie cookiePwd = CookieUtil.getCookie(cookies, "pwd");
                if (cookieUserName != null && cookiePwd != null) {
                    User user2 = new User();
                    user2.setUsername(cookieUserName.getValue());
                    user2.setPassword(cookiePwd.getValue());
                    User login = loginService.login(user2);
                    if (login != null) {
                        session.setAttribute("user", login);
                    }
                }
            }
        }
    }
}
