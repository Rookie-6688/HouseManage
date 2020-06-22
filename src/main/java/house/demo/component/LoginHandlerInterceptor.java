//package house.demo.component;
//
//import house.demo.bean.User;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Arrays;
//
////拦截器
//public class LoginHandlerInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Object user = request.getSession().getAttribute("user");
//        Object adminuser = request.getSession().getAttribute("adminuser");
//        if(request.getRequestURL().toString().contains("/House/overpay")){
//            return true;
//        }
//        if (request.getRequestURI().contains("admin")) {
//            if (adminuser == null) {
//                request.setAttribute("message", "没有登录权限");
//                request.getRequestDispatcher("/adminlogin").forward(request, response);
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            if (user == null) {
//                String uri = request.getRequestURI();
//                if (!uri.equals("/House/orderhouse") && !uri.equals("/House/collect") && !uri.contains("/loginout") && !uri.contains(".js") && !uri.contains(".css")) {
//                    String[] split = uri.split("/House/");
//                    request.getSession().setAttribute("lastURL", split[1]);
//                }
//                if (uri.contains("/House/getdetails&bid=")) {         //在这里设置放行是为了获取访问这个页面的URL
//                    return true;
//                }
//                request.setAttribute("message", "没有登录权限");
//                request.getRequestDispatcher("/login").forward(request, response);
//                return false;
//            } else {
//                return true;
//            }
//        }
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
//}
