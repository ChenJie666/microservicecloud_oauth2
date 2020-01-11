package com.hxr.springcloud.controller;

import com.hxr.springcloud.entities.Dept;
import com.hxr.springcloud.service.impl.DeptServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {

    @Autowired
    private DeptServiceImpl service;

    @RequestMapping("/dept/add")
    public boolean add(@RequestBody Dept dept) {
        return service.add(dept);
    }

    @RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
    @HystrixCommand
    public Dept get(@PathVariable Long id) {
        return service.get(id);
    }

    @RequestMapping(value = "/dept/list", method = RequestMethod.GET)
    public List<Dept> list() {
        return service.list();
    }

    //TODO 服务发现
    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/dept/discovery",method = RequestMethod.GET)
    public Object discovery() {
        List<String> services = client.getServices();   //TODO 获取Eureka所有的微服务
        System.out.println("********" + services);

        List<ServiceInstance> instances = client.getInstances("MICROSERVICECLOUD-DEPT");   //TODO 通过微服务名查找注册的所有主机的id、端口
        for (ServiceInstance instance : instances) {
            System.out.println(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort());
        }

        return this.client;
    }
}