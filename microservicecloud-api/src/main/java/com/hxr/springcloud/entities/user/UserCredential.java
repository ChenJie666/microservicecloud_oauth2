package com.hxr.springcloud.entities.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredential implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    /**
     * com.hxr.springcloud.entities.user.constants
     */
    private String type;
    private long userId;
}
