package com.Yukang.crm.workbench.controller;

import com.Yukang.crm.settings.domain.User;
import com.Yukang.crm.settings.service.UserService;
import com.Yukang.crm.settings.service.impl.UserServiceImpl;
import com.Yukang.crm.utils.*;
import com.Yukang.crm.vo.PaginationVo;
import com.Yukang.crm.workbench.domain.Activity;
import com.Yukang.crm.workbench.domain.ActivityRemark;
import com.Yukang.crm.workbench.service.ActivityService;
import com.Yukang.crm.workbench.service.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        response.setCharacterEncoding("UTF-8");
        if ("/workbench/activity/getUserList.do".equals(path)) {
            getUserList(request,response);


        }else if ("/workbench/activity/save.do".equals(path)) {
            save(request,response);

        }else if ("/workbench/activity/pageList.do".equals(path)) {
            pageList(request, response);
        }else if ("/workbench/activity/delete.do".equals(path)) {
            delete(request, response);
        }else if ("/workbench/activity/getUserListAndAActivity.do".equals(path)) {
            getUserListAndActivity(request,response);
        }else if ("/workbench/activity/update.do".equals(path)) {
            update(request,response);
        }else if ("/workbench/activity/detail.do".equals(path)) {
            detail(request,response);

        }else if ("/workbench/activity/getRemarkListById.do".equals(path)) {
            geRemarkListById(request,response);


        }else if ("/workbench/activity/deleteRemark.do".equals(path)){
            deleteRemark(request,response);
        }else if ("/workbench/activity/addRemark.do".equals(path)) {
            addRemark(request,response);

        }else if ("/workbench/activity/editRemark.do".equals(path)) {
            editRemark(request,response);

        }

    }

    private void addRemark(HttpServletRequest request, HttpServletResponse response) {

        String aid = request.getParameter("activityId");
        String noteContent = request.getParameter("noteContent");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        User user = (User)request.getSession().getAttribute("user");
        String createBy = user.getName();
        String editFlag = "0";

        ActivityRemark ar = new ActivityRemark();
        ar.setActivityId(aid);
        ar.setCreateBy(createBy);
        ar.setCreateTime(createTime);
        ar.setEditFlag(editFlag);
        ar.setId(id);
        ar.setNoteContent(noteContent);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = activityService.addRemark(ar);


        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("ar",ar);
        PrintJson.printJsonObj(response,map);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = activityService.deleteRemark(id);

        PrintJson.printJsonFlag(response, flag);
    }
    private void editRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");

        String editTime = DateTimeUtil.getSysTime();
        User user = (User)request.getSession().getAttribute("user");
        String editBy = user.getName();
        String editFlag = "1";

        ActivityRemark ar = new ActivityRemark();

        ar.setEditBy(editBy);
        ar.setEditTime(editTime);
        ar.setEditFlag(editFlag);
        ar.setId(id);
        ar.setNoteContent(noteContent);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = activityService.editRemark(ar);

        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("ar",ar);
        PrintJson.printJsonObj(response,map);

    }




    private void geRemarkListById(HttpServletRequest request, HttpServletResponse response) {
        String aid = request.getParameter("id");

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<ActivityRemark> remark = activityService.getRemarkListById(aid);
        PrintJson.printJsonObj(response, remark);


    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity a = activityService.detail(id);

        request.setAttribute("id", a);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);


    }

    private void update(HttpServletRequest request, HttpServletResponse response) {

        String id  = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        User user = (User)request.getSession().getAttribute("user");
        String editBy = user.getName();

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity a = new Activity();
        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setCreateTime(editTime);
        a.setCreateBy(editBy);
        boolean flag = activityService.update(a);
        PrintJson.printJsonFlag(response,flag);


    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String, Object> map = as.getUserListAndActivity(id);
        PrintJson.printJsonObj(response, map);


    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {

        String[] ids = request.getParameterValues("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.delete(ids);
        PrintJson.printJsonFlag(response, flag);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        int pageNo = Integer.valueOf(pageNoStr);
        int pageSize = Integer.valueOf(pageSizeStr);
        int skipCount = (pageNo-1) * pageSize;
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate",startDate);
        map.put("endDate", endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize", pageSize);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        PaginationVo<Activity> paginationVo = as.pageList(map);
        PrintJson.printJsonObj(response, paginationVo);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
       String id  = UUIDUtil.getUUID();
       String owner = request.getParameter("owner");
       String name = request.getParameter("name");
       String startDate = request.getParameter("startDate");
       String endDate = request.getParameter("endDate");
       String cost = request.getParameter("cost");
       String description = request.getParameter("description");
       String createTime = DateTimeUtil.getSysTime();
       User user = (User)request.getSession().getAttribute("user");
       String createBy = user.getName();

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity a = new Activity();
        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);
        boolean flag = activityService.save(a);
        PrintJson.printJsonFlag(response,flag);




    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("get user information");
        //为什么在市场活动模块还要调UserService，因为我们本质还是要调用用户这个模块

        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = userService.getUserList();
        PrintJson.printJsonObj(response,userList);

    }
}
