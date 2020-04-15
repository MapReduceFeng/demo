package com.example.demo.system.annotation;

/**
 * 功能描述:
 * 作者：MapReduce
 * 时间：2018/7/7
 */


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
@Documented
public @interface CreateTable {
    String dbId() default "";
}
