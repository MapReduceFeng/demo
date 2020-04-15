package com.example.demo.system.redis.service.impl;


import com.example.demo.system.annotation.RedisDB;
import com.example.demo.system.entity.RedisData;
import com.example.demo.system.entity.RedisSource;
import com.example.demo.system.redis.RedisUtil;
import com.example.demo.system.redis.service.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisServiceImp implements RedisService {
    @Resource
    private RedisUtil redisUtil;

    @Override
    @RedisDB(dbId = RedisData.db2, dbIdOne = RedisSource.db0)
    public String test() {
        redisUtil.set("test", "123456");
        return null;
    }
}
