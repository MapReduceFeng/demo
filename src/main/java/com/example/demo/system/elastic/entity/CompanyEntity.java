package com.example.demo.system.elastic.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
//@Document(indexName = "company", type = "workers", shards = 1, replicas = 0)
public class CompanyEntity {
    @Id
    private String id;
    //    @Field(type = FieldType.Keyword)
    private String title;
    //    @Field(type = FieldType.Text)
//    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;
    //    @Field(type = FieldType.Text)
    private String contentKey;
    //    @Field
    private Integer age = 0;
    //    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    private Date[] startEnd;

}
