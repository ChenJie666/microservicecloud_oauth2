package com.hxr.springcloud.service;

import com.hxr.springcloud.entities.Dept;

import java.util.List;

public interface DeptService {

    Boolean add(Dept dept);

    Dept get(Long id);

    List<Dept> list();

}
