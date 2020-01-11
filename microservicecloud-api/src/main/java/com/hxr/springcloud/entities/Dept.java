package com.hxr.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@SuppressWarnings("serial")
@AllArgsConstructor //全参构造器
@NoArgsConstructor  //空参构造器
@Data   //生成get/set函数
@Accessors(chain=true) //可以链式调用方法
public class Dept implements Serializable {

    private Long deptno;    //主键
    private String dname;   //部门名称
    private String db_source;   //数据库名称

//    public static void main(String[] args) {
//        Dept dept = new Dept();
//        dept.setDeptno(11L).setDname("RD").setDb_source("DB01");
//    }

}