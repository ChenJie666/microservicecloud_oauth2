package com.hxr.user.service;

import com.hxr.springcloud.entities.user.SysRole;

import java.util.Set;

public interface SysRoleService {

    Set<SysRole> findRolesByUserId(Long userId);

}
