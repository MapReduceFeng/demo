package com.example.demo.rabbit.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.support.CronSequenceGenerator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource(value = "classpath:${spring.classpath}/rabbit.properties", ignoreResourceNotFound = true)
public class RabbitConfig {
    public final static String DLX_EXCHANGENAME = "dlxExchangeName";//接收死信队列转发的共用交换机
    public final static String DLX_QUEUENAME = "dlxQueueName";//接收死信队列转发的队列
    public final static String EXCHANGENAME = "exchangeName";//共用交换机
    public final static String ROUTINGKEY = "#";//交换机根据key把消息送到相应的队列中

    @Bean
    @ConfigurationProperties(prefix = "spring.rabbitmq")
    public CachingConnectionFactory cachingConnectionFactory() {
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setUri("amqp://guest:guest@localhost:5672");
        return new CachingConnectionFactory();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(CachingConnectionFactory cachingConnectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(cachingConnectionFactory);
        rabbitAdmin.setIgnoreDeclarationExceptions(true);
        return rabbitAdmin;
    }

    public static Map<String, Object> getNextTime(String cron) {
        Map<String, Object> map = new HashMap<>();
        Date date = new Date();
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);
        Date nextDate = cronSequenceGenerator.next(date);
        //注意这两个参数必须绑定
        map.put("x-dead-letter-exchange", DLX_EXCHANGENAME);
        map.put("x-message-ttl", nextDate.getTime() - date.getTime());
        return map;
    }
}
