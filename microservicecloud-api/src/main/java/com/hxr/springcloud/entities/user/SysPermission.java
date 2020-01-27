package com.hxr.springcloud.entities.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysPermission implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Long id;
    private String permission;
    private String name;
    private Date createTime;
    private Date updateTime;

}
