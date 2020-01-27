package com.hxr.user.dao;

import com.hxr.springcloud.entities.user.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

@Mapper
public interface UserRoleDao {

    @Select(value = "select r.* from sys_role_user as ru inner join sys_role as r on ru.userId = r.id where ru.userId = #{userId}")
    Set<SysRole> findRolesByUserId(Long userId);

}
