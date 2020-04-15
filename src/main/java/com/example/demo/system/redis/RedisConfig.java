package com.example.demo.system.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
//@PropertySource(value = "classpath:config/redis.properties", ignoreResourceNotFound = true)
public class RedisConfig {
    @Bean("stringRedisTemplate")
    StringRedisTemplate stringRedisTemplate(@Qualifier("lettuceConnectionFactory_0") LettuceConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean("stringRedisTemplate_0")
    StringRedisTemplate stringRedisTemplate_0(@Qualifier("lettuceConnectionFactory_0") LettuceConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean("stringRedisTemplate_1")
    StringRedisTemplate stringRedisTemplate_1(@Qualifier("lettuceConnectionFactory_1") LettuceConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean("stringRedisTemplate_2")
    StringRedisTemplate stringRedisTemplate_2(@Qualifier("lettuceConnectionFactory_2") LettuceConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    private LettuceConnectionFactory getLettuceConnectionFactory(int i) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(i);
        redisStandaloneConfiguration.setHostName("39.98.145.243");
        redisStandaloneConfiguration.setPort(6379);

        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = LettuceClientConfiguration.builder();
        return new LettuceConnectionFactory(redisStandaloneConfiguration, builder.build());
    }

    @Bean("lettuceConnectionFactory_0")
    @Primary
    public LettuceConnectionFactory lettuceConnectionFactory_0() {
        LettuceConnectionFactory lettuceConnectionFactory = getLettuceConnectionFactory(0);
        return lettuceConnectionFactory;
    }

    @Bean("lettuceConnectionFactory_1")
    public LettuceConnectionFactory lettuceConnectionFactory_1() {
        LettuceConnectionFactory lettuceConnectionFactory = getLettuceConnectionFactory(1);
        return lettuceConnectionFactory;
    }

    @Bean("lettuceConnectionFactory_2")
    public LettuceConnectionFactory lettuceConnectionFactory_2() {
        LettuceConnectionFactory lettuceConnectionFactory = getLettuceConnectionFactory(2);
        return lettuceConnectionFactory;
    }

}