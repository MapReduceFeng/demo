package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@EnableDiscoveryClient
public class DemoApplication {

    public static void main(String[] args) {
//        System.setProperty("java.security.auth.login.config", "D:/IdeaProjects/demo/src/main/resources/kafka/kafka_client_jaas.conf");
//        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(DemoApplication.class, args);
    }

}
