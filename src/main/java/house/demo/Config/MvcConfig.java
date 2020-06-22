package house.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    //所有WebMvcConfigurer组件都会一起作用,所以我们可以自定义一个组件来配置跳转
    @Bean  //将组件注册到容器
    public WebMvcConfigurer WebMvcConfigurer() {
        WebMvcConfigurer configurer = new WebMvcConfigurer() {
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**", "/orderhouse").excludePathPatterns("/", "/login", "/register", "/user/login", "/getKaptchaImage", "/user/register",
//                        "/adminlogin", "/adminhome", "/error", "/findhouse&roomtype={roomtype}&high={high}&much={much}&livetype={livetype}&order={order}&num={num}"
//                        , "/assets/**");/*,"/getdetails&bid={bid}"*/
//            }

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                // 因为系统访问空请求默认会先在静态文件夹下找index页面，所以需要在我们在Thymeleaf模板里注册指定跳转
                //这里是第一层直接根据URL进行跳转，后面的Controller层不能return使用这里的映射(大概)
                registry.addViewController("/register").setViewName("register");
                registry.addViewController("/changeMyMessage").setViewName("MySpace/MyMessageChange");
                registry.addViewController("/adminlogin").setViewName("admin/index");
                registry.addViewController("/admintop").setViewName("admin/top");
                registry.addViewController("/adminleft").setViewName("admin/left");
                registry.addViewController("/adminwelcome").setViewName("admin/welcome");
                registry.addViewController("/adminbottom").setViewName("admin/bottom");
                registry.addViewController("/adminaddBednumber").setViewName("admin/House/add");
                registry.addViewController("/adminCollectAdd").setViewName("admin/Collect/add");
            }
            //过滤器注册，因为后面测试暂时注释
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/index.html","/","/user/login");
//            }


        };
        //过滤器注册，因为后面测试暂时注释
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/index.html","/","/user/login");
//            }
        return configurer;
    }

}
