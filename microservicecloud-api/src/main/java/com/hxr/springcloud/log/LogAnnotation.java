package com.hxr.springcloud.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO 不理解该自定义注解的作用？？？？？？
 *
 */
@Target({ElementType.METHOD})   //TODO 该注解的作用目标
@Retention(RetentionPolicy.RUNTIME) //TODO 该注解的生命周期
public @interface LogAnnotation {

    /**
     * 日志模块
     *
     * @return
     * @see com.hxr.springcloud.log.constants.LogModule
     */
    String module();

    /**
     * 记录参数<br>
     * 尽量记录普通参数类型的方法，和能序列化的对象
     *
     * @return
     */
    boolean recordParam() default true;

}
