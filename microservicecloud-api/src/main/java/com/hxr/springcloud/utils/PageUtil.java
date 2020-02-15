package com.hxr.springcloud.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Slf4j //TODO 用于log.info/error等信息的打印
public class PageUtil {

    public static final String START = "start";

    public static final String LENGTH = "length";

    /**
     * 转换并校验分页参数<br>
     * mybatis中limit #{start, JdbcType=INTEGER}, #{length,
     * JdbcType=INTEGER}里的类型转换貌似失效<br>
     * 我们这里先把他转成Integer的类型
     *
     * @param params
     * @param required  分页参数是否是必填的
     */
    public static void pageParamConver(Map<String,Object> params,Boolean required) {
        //TODO 如果分页参数是必填的，则需要进行校验
        if (required) {
            if (params == null || !params.containsKey(START) || !params.containsKey(LENGTH)) {
                throw new IllegalArgumentException("请检查分页参数," + START + "和" + LENGTH);
            }
        }

        //TODO 判断分页参数start和length是否符合要求，不符合重置为0并打印错误信息
        if (!CollectionUtils.isEmpty(params)) {

            if (params.containsKey(START)) {
                Integer start = (Integer) params.get(START);    //TODO 在Mapper配置文件中limit中指定jdbcType可能会失效，所以在这里直接转换为Integer类型
                if (start < 0) {
                    log.error("start: {},重置为0", start);
                    start = 0;
                }

                params.put(START, start);
            }

            if (params.containsKey(LENGTH)) {
                Integer length = (Integer) params.get(LENGTH);
                if (length < 0) {
                    log.error("length: {},重置为0",length);
                    length = 0;
                }

                params.put(LENGTH, length);
            }
        }

    }

}
