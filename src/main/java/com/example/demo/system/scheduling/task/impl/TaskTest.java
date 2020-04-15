package com.example.demo.system.scheduling.task.impl;

import com.example.demo.system.scheduling.task.Task;

public class TaskTest implements Task {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + ": " + i + "  TaskTest !!!!");
            } catch (Exception e) {
            }
        }

    }
}
