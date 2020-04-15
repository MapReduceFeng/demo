package com.example.demo.system.entity;

public enum RedisData {
    db0(0),
    db1(1),
    db2(2),
    db3(3),
    db4(4),
    db5(5),
    db6(6),
    db7(7),
    db8(8),
    db9(9),
    db10(10),
    db11(11),
    db12(12),
    db13(13),
    db14(14),
    db15(15);

    private RedisData(int id) {
        this.id = id;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
