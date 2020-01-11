package com.hxr.springcloud.dao;


import com.hxr.springcloud.entities.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptDao {

    boolean addDept(Dept dept);

    List<Dept> findAll();

    Dept findById(Long id);

}
