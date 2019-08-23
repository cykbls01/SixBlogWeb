package com.example.demo.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    //所有的webMvcConfigurerAdapter组件会一起起作用
    @Bean //註冊到容器去
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("Login");
                registry.addViewController("/index.html").setViewName("Login");
                registry.addViewController("/main.html").setViewName("Dashboard");
                registry.addViewController("/Register.html").setViewName("Register");
                registry.addViewController("/Findpwd.html").setViewName("Findpwd");
                registry.addViewController("/Login.html").setViewName("Login");
                registry.addViewController("/Success.html").setViewName("Success");
                registry.addViewController("/Bingo.html").setViewName("Bingo");
                registry.addViewController("/Modify.html").setViewName("Modify");
                registry.addViewController("/Upload.html").setViewName("Upload");
                registry.addViewController("/Zone.html").setViewName("Zone");
                registry.addViewController("/Zone_follow.html").setViewName("Zone_follow");
            }
            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //静态资源 css js 已经做好了静态资源映射
//                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").
//                        excludePathPatterns("/index.html","/","/user/login");
            }
        };
        return adapter;
    }


}
