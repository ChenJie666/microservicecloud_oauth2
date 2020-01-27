package com.hxr.user.service.impl;

import com.hxr.springcloud.entities.user.SysPermission;
import com.hxr.springcloud.entities.user.SysRole;
import com.hxr.user.dao.RolePermissionDao;
import com.hxr.user.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public Set<SysPermission> findByRoleIds(Set<SysRole> sysRoles) {

        return rolePermissionDao.findPermissionsByRoleId(sysRoles);
    }

}
