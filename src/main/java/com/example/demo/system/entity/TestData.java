package com.example.demo.system.entity;

import com.example.demo.system.entity.RespPage;
import com.example.demo.system.entity.TestEntity;

import java.util.List;

public class TestData extends RespPage {
    private List<TestEntity> list;

    public List<TestEntity> getList() {
        return list;
    }

    public void setList(List<TestEntity> list) {
        this.list = list;
    }
}
