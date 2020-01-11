package com.hxr.springcloud.controller;

import com.hxr.springcloud.entities.Dept;
import com.hxr.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeptController_Consumer {

    @Autowired
    private DeptClientService service;

    @RequestMapping(value = "/consumer/dept/get/{id}", method = RequestMethod.GET)
    public Dept get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @RequestMapping(value = "/consumer/dept/list", method = RequestMethod.GET)
    public List<Dept> list() {
        return service.list();
    }

    @RequestMapping(value = "/consumer/dept/add")
    public Boolean add(Dept dept) {
        return service.add(dept);
    }

    @RequestMapping(value = "/consumer/dept/discovery", method = RequestMethod.GET)
    public Object discovery() {
        return service.discovery();
    }

}
