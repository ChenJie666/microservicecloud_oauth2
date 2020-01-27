package com.hxr.user.dao;

import com.hxr.springcloud.entities.user.AppUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppUserDao {

    @Select(value = "select * from app_user where username = #{username}")
    AppUser findByUsername(String username);


    /**
     * 根据给定的参数params，统计app_user表中符合条件的用户数
     *
     * @param params
     * @return int
     */
    int count(Map<String,Object> params);  //TODO Mapper中的where标签会读取该参数params的值

    /**
     * 根据给定的参数params，将满足过滤条件和分页条件的用户以集合形式返回
     *
     * @param params
     * @return
     */
    List<AppUser> findData(Map<String,Object> params);

}
