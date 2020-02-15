package com.hxr.user.dao;

import com.hxr.springcloud.entities.user.AppUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
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
     * @return List<AppUser>
     */
    List<AppUser> findData(Map<String,Object> params);

    /**
     * 通过用户id查询用户的详细信息
     *
     * @param id
     * @return AppUser
     */
    @Select(value = "select * from app_user where id = #{id}")
    AppUser findById(Long id);

    /**
     * 将注册用户存储到数据库中
     *
     * @param appUser
     * @return int
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")   //TODO 插入数据后自动获取对应的字段的值然后给实例对象的指定属性赋值；是否对查询的数据进行缓存也是通过该注解进行开启
    @Insert(value = "insert into app_user(id,username,password,nickname,headImgUrl,phone,sex,enabled,type,createTime,updateTime)"
            + " values(#{id},#{username},#{password},#{nickname},#{headImgUrl},#{phone},#{sex},#{enabled},#{type},#{createTime},#{updateTime}) ")
    int save(AppUser appUser);

    /**
     * 更新对应的用户信息
     *
     * @param appUser
     * @return int
     */
    int update(AppUser appUser);



}
