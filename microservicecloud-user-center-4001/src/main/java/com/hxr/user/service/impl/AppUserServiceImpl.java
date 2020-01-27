package com.hxr.user.service.impl;

import com.hxr.springcloud.entities.common.Page;
import com.hxr.springcloud.entities.user.AppUser;
import com.hxr.springcloud.entities.user.LoginAppUser;
import com.hxr.springcloud.entities.user.SysPermission;
import com.hxr.springcloud.entities.user.SysRole;
import com.hxr.springcloud.utils.PageUtil;
import com.hxr.user.dao.AppUserDao;
import com.hxr.user.dao.UserCredentialsDao;
import com.hxr.user.dao.UserRoleDao;
import com.hxr.user.service.AppUserService;
import com.hxr.user.service.SysPermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserDao appUserDao;

    @Autowired
    private UserCredentialsDao userCredentialsDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private SysPermissionService sysPermissionService;


    /**
     * 获取用户名对应的LoginAppUser对象
     *
     * @param username
     * @return LoginAppUser
     */
    @Transactional  //TODO 启动事务
    @Override
    public LoginAppUser findByUsername(String username) {
        AppUser appUser = userCredentialsDao.findByUsername(username);  //TODO 从用户表中获取用户信息

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

}
