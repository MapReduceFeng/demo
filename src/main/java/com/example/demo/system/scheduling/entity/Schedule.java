package com.example.demo.system.scheduling.entity;


import java.io.Serializable;


public class Schedule implements Serializable {
    private static final long serialVersionUID = 1100643012066355335L;


    private Integer id; // 主键

    private String cron; // 表达式

    private String classPath; // 类名

    private String descriptions; // 描述......
    private int startOver; // 描述......


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCron() {
        return this.cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getClassPath() {
        return this.classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getDescriptions() {
        return this.descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public int getStartOver() {
        return startOver;
    }

    public void setStartOver(int startOver) {
        this.startOver = startOver;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", cron='" + cron + '\'' +
                ", classPath='" + classPath + '\'' +
                ", descriptions='" + descriptions + '\'' +
                '}';
    }
}
