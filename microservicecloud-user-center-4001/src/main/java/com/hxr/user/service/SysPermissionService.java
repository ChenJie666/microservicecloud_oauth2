package com.hxr.user.service;

import com.hxr.springcloud.entities.user.SysPermission;
import com.hxr.springcloud.entities.user.SysRole;

import java.util.Set;

public interface SysPermissionService {

    Set<SysPermission> findByRoleIds(Set<SysRole> sysRoles);

}
