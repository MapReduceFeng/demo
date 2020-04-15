package com.example.demo.system.elastic.service.impl;

import com.example.demo.system.elastic.dao.CompanyRepository;
import com.example.demo.system.elastic.entity.CompanyEntity;
import com.example.demo.system.elastic.service.ElasticService;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Log
public class ElasticServiceImpl implements ElasticService {

    @Resource
    private CompanyRepository companyRepository;

    @Override
    @Async()
    public void save(CompanyEntity companyEntity) {
        String content = null;
        //companyRepository.save(companyEntity);
    }


}

