package com.example.demo.rabbit.task.impl;


import com.example.demo.rabbit.task.RabbitTask;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RabbitTaskImplA implements RabbitTask {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void run() {
        System.out.println(sdf.format(new Date()) + " ............. ");
    }
}
