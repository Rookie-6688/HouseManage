package house.demo.Config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: lijincan
 * @date: 2020年02月26日 13:14
 * @Description: TODO
 */
@Configuration
public class ShiroConfig {

    //shiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);


        //设置登录请求
//        bean.setLoginUrl("/login");       //设置了登陆请求页面在没有任任何权限时访问有权限的请求时就会先进入
        /**
         * 将自定义的页面跳转设置进去
         */
        Map<String, Filter> filters = bean.getFilters();
        // 将重写的Filter注入到factoryBean的filter中
        filters.put("authc", new MyAuthenticationFilter());


        //登陆成功页面
//        bean.setSuccessUrl("/index");
        //设置未授权页面
        bean.setUnauthorizedUrl("/noauth");

        //添加shiro的内置过滤器
        /*
            anon: 无需认证就可访问
            authc：必须认证才能访问
            user：必须拥有记住我功能才能访问
            perms: 拥有对某个资源的权限才能访问
            role:拥有某个角色权限才能访问
       */

        Map<String, String> filterMap = new LinkedHashMap<>();

        filterMap.put("/login","anon");     //配置登陆请求不需要认证 annon表示某个请求不需要认证
        filterMap.put("/","anon");
        filterMap.put("/findhouse*","anon");
        filterMap.put("/getdetails&bid=*","anon");
        filterMap.put("/adminhome","anon");
        filterMap.put("/adminlogin","anon");
        filterMap.put("/getKaptchaImage","anon");
        filterMap.put("/user/login","anon");
        filterMap.put("/register","anon");
        filterMap.put("/sendMsg","anon");
        filterMap.put("/validateNum","anon");
        filterMap.put("/admintop","anon");

        filterMap.put("/assets/**","anon");
        filterMap.put("/plugins/**","anon");
        filterMap.put("/static8.ziroom.com/**","anon");
        //设置登出
//        filterMap.put("/logout", "logout");     //配置登出，登出后会清空当前用户的内存
        filterMap.put("/admin*","authc,roles[admin]");  //admin开头的网址需要有admin的角色才能访问

        filterMap.put("/**","authc,roles[user],user");           //配置剩余的请求全部需要进行登陆（必须配置在最后），可选的配置
        //授权
        bean.setFilterChainDefinitionMap(filterMap);
        return bean;
    }

    //DafaultWebSecurituManager
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
//        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }
    //创建realm对象 ，需要自定义
    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //整合thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

//    //会话管理器
//    @Bean
//    public SessionManager sessionManager() {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setSessionIdUrlRewritingEnabled(true);
//        sessionManager.setGlobalSessionTimeout(1 * 60 * 60 * 1000);
//        sessionManager.setDeleteInvalidSessions(true);
//        sessionManager.setSessionIdCookie(rememberMeCookie());
//        return sessionManager;
//    }
//
//
//    //缓存管理
//    @Bean
//    public EhCacheManager cacheManager() {
//        EhCacheManager cacheManager = new EhCacheManager();
//        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
//        return cacheManager;
//    }
//
//    //密码管理
//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        credentialsMatcher.setHashAlgorithmName("md5"); //散列算法使用md5
//        credentialsMatcher.setHashIterations(2);        //散列次数，2表示md5加密两次
//        credentialsMatcher.setStoredCredentialsHexEncoded(true);
//        return credentialsMatcher;
//    }
//
//    //cookie管理
//    @Bean
//    public SimpleCookie rememberMeCookie() {
//        SimpleCookie cookie = new SimpleCookie("rememberMe");
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(1 * 60 * 60);
//        return cookie;
//    }
//
//    //记住我
//    @Bean
//    public CookieRememberMeManager rememberMeManager(){
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(rememberMeCookie());
//        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));//这个地方我也没搞明白，为什么就是24位
//        return cookieRememberMeManager;
//    }

}
