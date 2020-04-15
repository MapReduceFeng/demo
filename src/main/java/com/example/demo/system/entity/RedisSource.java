package com.example.demo.system.entity;

public enum RedisSource {
    db0("0"),
    db1("1"),
    db2("2");

    private RedisSource(String id) {
        this.id = id;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
