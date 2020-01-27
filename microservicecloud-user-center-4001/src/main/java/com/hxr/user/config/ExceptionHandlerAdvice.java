package com.hxr.user.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 接收并处理所有的参数异常响应，并将异常信息封装后返回给请求端
 * 不然请求端只能看见500服务器异常
 */

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST) //TODO 此注解用于改变服务器的状态响应码，code中修改response状态码，reason中可以添加response的信息，注解底层还是通过设置 response属性来实现
    public Map<String,Object> badRequestException(IllegalArgumentException exception){
        Map<String, Object> data = new HashMap<>();
        data.put("code", HttpStatus.BAD_REQUEST.value());
        data.put("message", exception.getMessage());

        return data;    //TODO 将故障信息封装并返回，通过注解将响应码改为400
    }

}
