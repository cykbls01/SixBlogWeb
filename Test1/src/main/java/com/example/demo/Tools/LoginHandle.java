package com.example.demo.Tools;

import com.example.demo.Entity.User;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginHandle  implements HandlerInterceptor {

        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            HttpSession session = request.getSession();

            User user = (User) session.getAttribute("user");

            if (user == null){
                request.setAttribute("msg", "没权限请先登录");
                request.getRequestDispatcher("Login.html").forward(request, response);
                return false;
            }else {
                return true;
            }
        }

        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        }

        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        }
}






