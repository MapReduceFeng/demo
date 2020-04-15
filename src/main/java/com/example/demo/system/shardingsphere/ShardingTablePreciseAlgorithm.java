package com.example.demo.system.shardingsphere;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

public class ShardingTablePreciseAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<Long> shardingValue) {
        System.out.println("分表id: " + shardingValue.getValue() + " " + availableTargetNames);
        for (String each : availableTargetNames) {
            if (each.endsWith(String.valueOf(shardingValue.getValue() % availableTargetNames.size()))) {
                return each;
            }
        }
        return shardingValue.getLogicTableName();
    }
}
