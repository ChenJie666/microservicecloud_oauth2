package com.hxr.user.dao;

import com.hxr.springcloud.entities.user.AppUser;
import com.hxr.springcloud.entities.user.UserCredential;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserCredentialsDao {

    //TODO 为什么需要关联两张表，直接从app_user表查找用户不行吗？？？？？？？
    @Select(value = "select u.* from user_credentials as uc inner join app_user as u on uc.userId = u.id on where uc.username = #{username}")
    AppUser findUserByUsername(String username);

    @Select(value = "select * from user_credentials where username = #{username}")
    UserCredential findByUsername(String username);

    @Select(value = "insert into user_credentials(username,type,userId) values(#{username},#{type},#{userId})")
    int save(UserCredential userCredential);

}
