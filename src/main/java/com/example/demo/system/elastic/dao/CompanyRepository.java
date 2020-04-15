package com.example.demo.system.elastic.dao;

import com.example.demo.system.elastic.entity.CompanyEntity;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
//public interface CompanyRepository extends ElasticsearchRepository<CompanyEntity,String> {
public interface CompanyRepository {
    CompanyEntity queryCompanyById(String id);

    void saveAll(ArrayList<CompanyEntity> list);

    Page<CompanyEntity> search(QueryBuilder queryBuilder, PageRequest pageRequest);

    void delete(CompanyEntity companyEntity);

    Object save(CompanyEntity companyEntity);
}
