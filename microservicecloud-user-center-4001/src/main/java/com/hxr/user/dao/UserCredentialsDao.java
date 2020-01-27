package com.hxr.user.dao;

import com.hxr.springcloud.entities.user.AppUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserCredentialsDao {

    @Select(value = "select u.* from user_credentials as uc inner join app_user as u on uc.userId = u.id on where uc.username = #{username}")
    AppUser findByUsername(String username);

}
