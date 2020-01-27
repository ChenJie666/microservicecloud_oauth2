package com.hxr.springcloud.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
public class AppUser implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String headImgUrl;
    private String phone;
    private Integer sex;
    /**
     * 状态
     */
    private boolean enabled;
    private String type;
    private Date createTime;
    private Date updateTime;

}
