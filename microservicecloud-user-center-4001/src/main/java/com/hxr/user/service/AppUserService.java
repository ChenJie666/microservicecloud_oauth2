package com.hxr.user.service;

import com.hxr.springcloud.entities.common.Page;
import com.hxr.springcloud.entities.user.AppUser;
import com.hxr.springcloud.entities.user.LoginAppUser;

import java.util.Map;

public interface AppUserService {

    LoginAppUser findByUsername(String username);

    Page<AppUser> findUsers(Map<String,Object> params);

}
