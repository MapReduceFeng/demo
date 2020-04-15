package com.example.demo.system.shardingsphere;

import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class AutomaticKeyGenerator implements ShardingKeyGenerator {
    @Override
    public Comparable<?> generateKey() {
        return KeyGeneratorUtils.generateKey();
    }

    @Override
    public String getType() {
        return "AutomaticKeyGenerator";
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
