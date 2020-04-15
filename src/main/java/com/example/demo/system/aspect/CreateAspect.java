package com.example.demo.system.aspect;

import com.example.demo.user.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/*
 *@url:www.com.mypoint.aspectj.MyAspectj
 *@author:MapReduce
 *@date:2018/8/14
 *@Description:
 */
@Component
@Aspect
public class CreateAspect {
    public static final Logger logger = LoggerFactory.getLogger(CreateAspect.class);
    @Resource
    private UserService userService;

    @Pointcut("@annotation(com.example.demo.system.annotation.CreateTable)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object object = null;
        try {
            object = joinPoint.proceed();
        } catch (Throwable throwable) {
            String[] split = throwable.getCause().getMessage().split("'");
            if (split.length > 0 && "Table ".equals(split[0])) {
                userService.createTables(split[1], split[1].substring(0, split[1].length() - 1));
            }
            object = joinPoint.proceed();
        }
        return object;
    }
}
