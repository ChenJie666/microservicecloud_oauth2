package com.hxr.user.service.impl;

import com.hxr.springcloud.entities.common.Page;
import com.hxr.springcloud.entities.user.*;
import com.hxr.springcloud.entities.user.constants.CredentialType;
import com.hxr.springcloud.entities.user.constants.UserType;
import com.hxr.springcloud.utils.PageUtil;
import com.hxr.springcloud.utils.PhoneUtil;
import com.hxr.user.dao.AppUserDao;
import com.hxr.user.dao.UserCredentialsDao;
import com.hxr.user.dao.UserRoleDao;
import com.hxr.user.service.AppUserService;
import com.hxr.user.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserDao appUserDao;

    @Autowired
    private UserCredentialsDao userCredentialsDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    /**
     * 获取用户名对应的LoginAppUser对象
     *
     * @param username
     * @return LoginAppUser
     */
    @Transactional  //TODO 启动事务
    @Override
    public LoginAppUser findByUsername(String username) {
        AppUser appUser = userCredentialsDao.findUserByUsername(username);  //TODO 从user_credentials和用户表中获取用户信息

        if (appUser != null) {
            LoginAppUser loginAppUser = new LoginAppUser();
            BeanUtils.copyProperties(appUser, loginAppUser); //TODO 将得到的AppUser对象的属性复制给LoginAppUser对象

            Set<SysRole> sysRoles = userRoleDao.findRolesByUserId(appUser.getId());
            loginAppUser.setSysRoles(sysRoles); //TODO 将获取到的角色集合放入样例类中

            if (!CollectionUtils.isEmpty(sysRoles)) {
                Set<Long> rolesIds = sysRoles.parallelStream().map(r -> r.getId()).collect(Collectors.toSet()); //TODO 根据该用户的所拥有的角色用多线程查询出所有的权限
                Set<SysPermission> sysPermissions = sysPermissionService.findByRoleIds(sysRoles);

                if (!CollectionUtils.isEmpty(sysPermissions)) {
                    Set<String> permissions = sysPermissions.parallelStream().map(p -> p.getPermission()).collect(Collectors.toSet());

                    loginAppUser.setPermissions(permissions); //TODO 将获取到的权限集合放入样例类中
                }
            }

            return loginAppUser; //TODO 最终返回LoginAppUser对象，包含用户信息和用户角色及权限信息
        }

        return null;
    }

    /**
     * 将满足参数params的用户数和用户信息封装为Page对象返回
     *
     * @param params
     * @return Page
     */
    public Page<AppUser> findUsers(Map<String,Object> params){

        int total = appUserDao.count(params);  //TODO 统计符合参数条件的用户数

        List<AppUser> list = Collections.emptyList();//TODO 是Collections类的内部类，size为0，不可改变。好出：1.节省空间 2.元素为空时返回该对象，方便方法调用者(不需要判断方法返回值是否为null)

        if (total > 0) {
            PageUtil.pageParamConver(params,true);  //TODO 对参数params进行处理

            list = appUserDao.findData(params); //TODO 查找到的分页后的数据
        }

        return new Page<>(total,list);  //TODO 将查询结果封装为Page对象并返回
    }

    /**
     * 通过用户id查询该用户的详细信息
     *
     * @param id
     * @return AppUser
     */
    @Override
    public AppUser findById(Long id) {
        return appUserDao.findById(id);
    }

    /**
     * 用户进行注册时对注册信息进行检查并写入到数据库中
     *
     * @param appUser
     */
    @Transactional
    @Override
    public void addAppUser(AppUser appUser) {
        String username = appUser.getUsername();
        if (StringUtils.isBlank(username)) {    //TODO isBlank将ascii码32以下的字符判断为空，而isEmpty只判断是否为 null 或 ""
            throw new IllegalArgumentException("用户名不能为空");
        }

        if (PhoneUtil.checkPhone(username)) {
            throw new IllegalArgumentException("不能将手机号作为用户名");
        }

        if (username.contains("@")) {
            throw new IllegalArgumentException("用户名不能包含@符号");
        }

        if (username.contains("|")) {
            throw new IllegalArgumentException("用户名不能包含|符号");
        }

        if (StringUtils.isBlank(appUser.getPassword())) {
            throw new IllegalStateException("密码不能为空");
        }

        if (StringUtils.isBlank(appUser.getNickname())) {
            appUser.setNickname(username);  //如果没有设置昵称，将用户名设为昵称
        }

        if (StringUtils.isBlank(appUser.getType())) {
            appUser.setType(UserType.APP.name());
        }

        UserCredential userCredential = userCredentialsDao.findByUsername(username);
        if (userCredential != null) {
            throw new IllegalArgumentException("用户名存在");
        }

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setEnabled(Boolean.TRUE);
        appUser.setCreateTime(new Date());
        appUser.setUpdateTime(new Date());

        appUserDao.save(appUser);
        userCredentialsDao.save(new UserCredential(appUser.getUsername(),CredentialType.USERNAME.name(), appUser.getId()));

        log.info("添加用户: {}",appUser);
    }

    /**
     * 更新用户自己的个人信息
     *
     * @param appUser
     */
    @Transactional
    @Override
    public void updateAppUser(AppUser appUser) {
        appUser.setUpdateTime(new Date());

        appUserDao.update(appUser);
        log.info("修改用户：{}",appUser);
    }

    /**
     * 修改用户自己的个人密码
     *
     * @param id
     * @param oldPassword
     * @param newPassword
     */
    @Override
    public void updatePassword(Long id, String oldPassword, String newPassword) {

    }
}
