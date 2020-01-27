package com.hxr.user.controller;


import com.hxr.springcloud.entities.common.Page;
import com.hxr.springcloud.entities.user.AppUser;
import com.hxr.springcloud.entities.user.LoginAppUser;
import com.hxr.springcloud.utils.AppUserUtil;
import com.hxr.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private AppUserService appUserService;

    /**
     * 当前登录用户的LoginAppUser。接口都是基于access_token交互的，可以将token放到参数中，也可以放到head中。
     *
     * @return LoginAppUser
     */
    @GetMapping("/users/current")
    public LoginAppUser getLoginAppUser(){
        return AppUserUtil.getLoginAppUser();
    }

    /**
     * 输入用户名密码后，通过username从用户中心获取该用户的LoginAppUser对象。
     *
     * @param username
     * @return LoginAppUser
     */
    @GetMapping(value = "/users-anon/internal",params = "username")
    public  LoginAppUser findByUserName(String username){
        return appUserService.findByUsername(username);
    }

    /**
     * 通过查询条件返回分页信息
     *
     * @param params
     * @return Page<AppUser>
     */
    @PreAuthorize("hasAuthority('back:user:query')")
    @GetMapping("/users")
    public Page<AppUser> findUsers(@RequestParam Map<String, Object> params) {
        return appUserService.findUsers(params);
    }


}
