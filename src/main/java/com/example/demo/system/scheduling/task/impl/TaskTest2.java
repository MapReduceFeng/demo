package com.example.demo.system.scheduling.task.impl;

import com.example.demo.system.scheduling.task.Task;

public class TaskTest2 implements Task {
    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + i + "  TaskTest2 !!!!");
            } catch (Exception e) {
            }
        }
    }
}
