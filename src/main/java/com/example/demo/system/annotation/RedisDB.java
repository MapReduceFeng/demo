package com.example.demo.system.annotation;

/**
 * 功能描述:
 * 作者：MapReduce
 * 时间：2018/7/7
 */


import com.example.demo.system.entity.RedisData;
import com.example.demo.system.entity.RedisSource;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
@Documented
public @interface RedisDB {
    RedisData dbId() default RedisData.db0;

    RedisSource dbIdOne() default RedisSource.db0;
}
