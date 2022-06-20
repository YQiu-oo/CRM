package com.Yukang.crm.utils.filter;

import com.Yukang.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IllegalVisitFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Check whether in the same session ");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
         String path = request.getServletPath();
        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)) {
            filterChain.doFilter(servletRequest,servletResponse);

        }else{
            User user = (User) request.getSession().getAttribute("user");

            if (user != null) {
                System.out.println(1111);
                filterChain.doFilter(servletRequest,servletResponse);
            }else {
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
        }
        //为什么使用重定向，转发不行吗？

        /*
        转发之后， 路径会停留在老路径上， 而不是跳转之后最新资源的路径。
        我们应该在为用户跳转到登录页的同时， 将浏览器的地址栏应该自动设置为当前的登陆页面。
         */

    }


}