package com.hxr.user.dao;

import com.hxr.springcloud.entities.user.SysPermission;
import com.hxr.springcloud.entities.user.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface RolePermissionDao {


    Set<SysPermission> findPermissionsByRoleId(Set<SysRole> sysRoles);

}
