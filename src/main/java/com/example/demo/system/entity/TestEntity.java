package com.example.demo.system.entity;

public class TestEntity {
    private String id;
    private String name;
    private String action;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public TestEntity() {
    }

    public TestEntity(String id, String name, String action) {
        this.id = id;
        this.name = name;
        this.action = action;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
