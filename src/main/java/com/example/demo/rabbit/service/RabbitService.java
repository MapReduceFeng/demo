package com.example.demo.rabbit.service;

import com.example.demo.rabbit.entity.RabbitEntity;

import java.util.HashMap;
import java.util.List;

public interface RabbitService {
    HashMap<String, Object> getData(RabbitEntity rabbitEntity);

    List<RabbitEntity> getDataByStatus(RabbitEntity rabbitEntity);

    RabbitEntity getDataById(RabbitEntity rabbitEntity);

    int addData(RabbitEntity rabbitEntity);

    int updateData(RabbitEntity rabbitEntity);

    int updateDataStatus(RabbitEntity rabbitEntity);

    List<RabbitEntity> getDataByName(RabbitEntity rabbitEntity);
}
