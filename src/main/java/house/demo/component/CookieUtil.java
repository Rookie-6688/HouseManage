package house.demo.component;

import javax.servlet.http.Cookie;

public class CookieUtil {

    public static Cookie getCookie(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        } else {
            return null;
        }
        return null;
    }
}
