package com.example.demo.system.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@PropertySource(value = "classpath:config/dev/consts.properties")
//@ConfigurationProperties(prefix = "spring.test.session")
public class EntityConsts {
    @Value("${spring.test.session.url}")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getList() {
        List<String> split = Arrays.asList(this.getUrl().split(","));
        return split;
    }

    @Override
    public String toString() {
        return "EntityConsts{" +
                "url='" + url + '\'' +
                '}';
    }
}
