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
    public boolean add(@RequestBody Dept dept) {    //TODO @RequestBody不加，直接访问服务可以成功添加，但是RestTemplate添加为空；@RequestBody加上，直接访问服务报type=Bad Request, status=400，通过RestTemplate添加正常
        return deptService.add(dept);
    }

    @RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
    @HystrixCommand
    public Dept get(@PathVariable Long id) {     //TODO 获取占位符的值
        return deptService.get(id);
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
