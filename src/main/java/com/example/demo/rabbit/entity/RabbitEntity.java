package com.example.demo.rabbit.entity;

import java.io.Serializable;

public class RabbitEntity extends BasePage implements Serializable {
    private int id;
    private String queueName;
    private String classPackage;
    private String cron;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(String classPackage) {
        this.classPackage = classPackage;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RabbitEntity() {
    }

    public RabbitEntity(int id, String queueName, String classPackage, String cron) {
        this.id = id;
        this.queueName = queueName;
        this.classPackage = classPackage;
        this.cron = cron;
    }

    @Override
    public String toString() {
        return "RabbitEntity{" +
                "id=" + id +
                ", queueName='" + queueName + '\'' +
                ", classPackage='" + classPackage + '\'' +
                ", cron='" + cron + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
