package com.Yukang.crm.settings.service;

import com.Yukang.crm.exception.LoginException;
import com.Yukang.crm.settings.domain.User;

public interface UserService {
    User login(String loginAct, String password, String ip) throws LoginException;
}
