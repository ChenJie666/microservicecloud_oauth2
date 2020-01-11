package com.hxr.springcloud.controller;

import com.hxr.springcloud.entities.Dept;
import com.hxr.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping(value = "/dept/add")
    public boolean add(@RequestBody Dept dept) {
        return deptService.add(dept);
    }

    @RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
    //TODO 一旦调用服务方法失败并抛出异常，会自动调用@HystrixCommand标注好的fallbackMethod调用类中的指定方法
    @HystrixCommand(fallbackMethod = "processHystrix_Get")
    public Dept get(@PathVariable Long id) {     //TODO 获取占位符的值
        Dept dept = deptService.get(id);
        if (dept == null) {
            throw new RuntimeException("该id：" + id + "没有对应的信息");
        }
        return dept;
    }

    public Dept processHystrix_Get(@PathVariable Long id) {
        return new Dept().setDeptno(id)
                .setDname("该id：" + id + "没有对应的信息,null--@HystrixCommand")
                .setDb_source("no this database in DB01"); //TODO 返回由异常信息赋值的Dept类
    }


    @RequestMapping(value = "/dept/list", method = RequestMethod.GET)
    public List<Dept> list() {
        return deptService.list();
    }


    //TODO 服务发现
    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/dept/discovery", method = RequestMethod.GET)
    public Object discovery() {
        List<String> list = client.getServices();   //TODO Eureka中所有的微服务
        System.out.print("********" + list);

        List<ServiceInstance> srvList = client.getInstances("MICROSERVICECLOUD-DEPT");  //TODO 通过微服务名称查找并打印服务id，主机和端口
        for (ServiceInstance element : srvList) {
            System.out.println(element.getServiceId() + "\t" + element.getHost() + "\t" + element.getPort() + "\t");
        }
        return this.client;
    }

}
