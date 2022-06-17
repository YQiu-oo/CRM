package com.Yukang.crm.settings.service.impl;

import com.Yukang.crm.exception.LoginException;
import com.Yukang.crm.settings.dao.UserDao;
import com.Yukang.crm.settings.domain.User;
import com.Yukang.crm.settings.service.UserService;
import com.Yukang.crm.utils.DateTimeUtil;
import com.Yukang.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private  UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);


    @Override
    public User login(String loginAct, String password, String ip) throws LoginException{
        Map<String, String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",password);
        User user = userDao.login(map);
        if (user == null) {
            throw new LoginException("登陆失败");
        }
        if (user.getExpireTime().compareTo(DateTimeUtil.getSysTime()) < 0) {
            throw new LoginException("账号失效");
        }
        if ("0".equals(user.getLockState())) {
            throw new LoginException("账号已锁定");
        }
        if (!user.getAllowIps().contains(ip)) {
            throw new LoginException("IP地址不允许");
        }
        return user;

    }
}
