package com.hxr.user.controller;


import com.hxr.springcloud.entities.common.Page;
import com.hxr.springcloud.entities.user.AppUser;
import com.hxr.springcloud.entities.user.LoginAppUser;
import com.hxr.springcloud.log.LogAnnotation;
import com.hxr.springcloud.utils.AppUserUtil;
import com.hxr.user.service.AppUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 通过用户id获取用户的详细信息
     *
     * @param id
     * @return AppUser
     */
    @PreAuthorize("hasAuthority('back:user:query')")
    @GetMapping("/users/{id}")
    public AppUser findById(@PathVariable Long id){
        return appUserService.findById(id);
    }

    /**
     * 根据Post请求的请求体中的用户信息进行用户注册
     *
     * @param appUser
     * @return AppUser
     */
    @PostMapping("/users-anon/register")
    public AppUser register(@RequestBody AppUser appUser) {
        //用户名等信息的判断逻辑放在service中
        appUserService.addAppUser(appUser);

        return appUser; //返回的对象中补全了用户的id数据
    }

    /**
     * 更新用户自己的个人信息
     *
     * @param appUser
     * @return
     */
    @LogAnnotation(module = "修改个人信息")
    @PutMapping("/users/me") //TODO 作用同@PostMapping，都用用于向服务器提交信息，如果是添加信息则建议使用@PostMapping，如果是修改信息则建议使用@PutMapping
    public AppUser updateMe(@RequestBody AppUser appUser){
        LoginAppUser user = AppUserUtil.getLoginAppUser();  //TODO 从上下文中获取当前用户的LoginAppUser对象？？？
        appUser.setId(user.getId());

        appUserService.updateAppUser(appUser);

        return appUser;
    }

    /**
     * 修改用户自己的个人密码
     *
     * @param oldPassword
     * @param newPassword
     */
    @LogAnnotation(module = "修改个人密码")
    @PutMapping(value = "/users/password",params = {"oldPassword","newPassword"})  //TODO 需要在请求中带上这两个参数才能访问该接口
    public void updatePassword(String oldPassword,String newPassword){
        if (StringUtils.isBlank(oldPassword)) {
            throw new IllegalStateException("旧密码不能为空");
        }
        if (StringUtils.isBlank(newPassword)) {
            throw new IllegalStateException("新密码不能为空");
        }

        LoginAppUser user = AppUserUtil.getLoginAppUser();
        appUserService.updatePassword(user.getId(), oldPassword, newPassword);
    }

}
