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
     * 状态,此处是boolean类型，写入msyql数据库时会自动转换为tinyint(1)类型，1表示TRUE，0表示FALSE。
     */
    private boolean enabled;
    /**
     * com.hxr.springcloud.entities.user.constants
     */
    private String type;
    private Date createTime;
    private Date updateTime;

}
