package house.demo.Config.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MyAuthenticationFilter extends FormAuthenticationFilter {


    private static final String USR_LOGIN_URL = "/login";
    private static final String ADMIN_LOGIN_URL = "/adminlogin";



    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();

        if(url.contains("/admin"))
            WebUtils.issueRedirect(request, response, ADMIN_LOGIN_URL);
        else
            WebUtils.issueRedirect(request, response, USR_LOGIN_URL);
    }



}

