package com.example.demo.system.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/*
 *@url:www.com.mypoint.aspectj.MyAspectj
 *@author:MapReduce
 *@date:2018/8/14
 *@Description:
 */
@Component
@Aspect
public class CustomerAspect {
    public static final Logger logger = LoggerFactory.getLogger(CustomerAspect.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PostMapping)"
    )
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("controller before ......");
        Object object = joinPoint.proceed();
        stopWatch.stop();
        logger.info(" controller after ...... " + stopWatch.getTotalTimeMillis());
        return object;
    }
}
