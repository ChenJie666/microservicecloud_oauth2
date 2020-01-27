package com.hxr.user.service.impl;

import com.hxr.springcloud.entities.user.SysRole;
import com.hxr.user.dao.UserRoleDao;
import com.hxr.user.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public Set<SysRole> findRolesByUserId(Long userId) {
        return userRoleDao.findRolesByUserId(userId);
    }

}
