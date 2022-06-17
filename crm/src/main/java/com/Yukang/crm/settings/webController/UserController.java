package com.Yukang.crm.settings.webController;

import com.Yukang.crm.settings.domain.User;
import com.Yukang.crm.settings.service.UserService;
import com.Yukang.crm.settings.service.impl.UserServiceImpl;
import com.Yukang.crm.utils.MD5Util;
import com.Yukang.crm.utils.PrintJson;
import com.Yukang.crm.utils.ServiceFactory;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        response.setCharacterEncoding("UTF-8");
        if ("/settings/user/login.do".equals(path)) {
            login(request,response);


        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        String loginAct = request.getParameter("loginAct");
        String password = request.getParameter("loginPwd");
        password = MD5Util.getMD5(password);

        String ip = request.getRemoteAddr();

        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());



        try {
            User user = userService.login(loginAct , password,ip);

            request.getSession().setAttribute("user",user);
            PrintJson.printJsonFlag(response, true);

        }catch (Exception e){

            Map<String, Object> stringObjectHashMap = new HashMap<String, Object>();
            stringObjectHashMap.put("success", false);
            stringObjectHashMap.put("msg",e.getMessage());
            PrintJson.printJsonObj(response, stringObjectHashMap);



            e.printStackTrace();
        }



    }
}
