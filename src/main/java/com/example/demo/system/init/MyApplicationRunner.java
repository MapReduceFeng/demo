package com.example.demo.system.init;

import com.example.demo.system.scheduling.controller.SchedulingController;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component//被spring容器管理
@Order(1)//如果多个自定义ApplicationRunner，用来标明执行顺序
public class MyApplicationRunner implements ApplicationRunner {
    @Resource
    private SchedulingController schedulingController;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println("开机启动!!!!!!");
//        schedulingController.schedulingInit();

    }
}
