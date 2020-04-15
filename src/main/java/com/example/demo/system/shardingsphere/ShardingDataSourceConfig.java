package com.example.demo.system.shardingsphere;


import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.encrypt.EncryptColumnRuleConfiguration;
import org.apache.shardingsphere.api.config.encrypt.EncryptRuleConfiguration;
import org.apache.shardingsphere.api.config.encrypt.EncryptTableRuleConfiguration;
import org.apache.shardingsphere.api.config.encrypt.EncryptorRuleConfiguration;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.example.demo.**.dao")
@PropertySource(value = "classpath:${spring.classpath}/jdbc.properties", ignoreResourceNotFound = true)
public class ShardingDataSourceConfig {
    private final Environment environment;

    public ShardingDataSourceConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "ds0")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.ds0")
    public DataSource dataSource0() {
        return new HikariDataSource();
    }

    /* @Bean(name = "ds1")
     @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.ds1")
     public DataSource dataSource1() {
         return new HikariDataSource();
     }

     @Bean(name = "ds2")
     @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.ds2")
     public DataSource dataSource2() {
         return new HikariDataSource();
     }
 */
    @Primary
    @Bean(name = "shardingDataSources")
    public DataSource getDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.setEncryptRuleConfig(getEncryptRuleConfiguration());// 配置脱敏规则
        shardingRuleConfig.setDefaultDataSourceName("ds0");//默认数据源
        shardingRuleConfig.getTableRuleConfigs().add(getRuleUser());//表规则配置
//        shardingRuleConfig.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfigurations());//主从
        shardingRuleConfig.setDefaultKeyGeneratorConfig(new KeyGeneratorConfiguration("AutomaticKeyGenerator", "id", getProperties()));//分布式主键无效
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", new ShardingDatabaseAlgorithm()));//默认分库
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", new ShardingTablePreciseAlgorithm()));//默认分表
        Map<String, DataSource> dataSourceMap = new HashMap<>();//添加数据源
        dataSourceMap.put("ds0", dataSource0());
//        dataSourceMap.put("ds1", dataSource1());
//        dataSourceMap.put("ds2", dataSource2());

        Properties properties = new Properties();
        properties.put("sql.show", true);//#打印sql
        properties.put("query.with.cipher.comlum", "true");//#是否使用密文列查询
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, properties);
    }

    private static TableRuleConfiguration getRuleUser() {
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("users", "ds$->{0}.users$->{0..2}");//ds$->{0}.users不分表
        return tableRuleConfiguration;
    }

    // 配置脱敏规则
    private static EncryptRuleConfiguration getEncryptRuleConfiguration() {
        Properties props = new Properties();
        props.setProperty("aes.key.value", "123456");
        EncryptorRuleConfiguration encryptorConfig = new EncryptorRuleConfiguration("AES", props);
        EncryptColumnRuleConfiguration columnConfig = new EncryptColumnRuleConfiguration("userName", "userPass", "", "aes");
        EncryptTableRuleConfiguration tableConfig = new EncryptTableRuleConfiguration(Collections.singletonMap("userName", columnConfig));
        EncryptRuleConfiguration encryptRuleConfig = new EncryptRuleConfiguration();
        encryptRuleConfig.getEncryptors().put("aes", encryptorConfig);
        encryptRuleConfig.getTables().put("users", tableConfig);
        return encryptRuleConfig;
    }

    //主从
    private List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations() {
        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration("ds", "ds0", Arrays.asList("ds1"));//主从不能是自己
        return Lists.newArrayList(masterSlaveRuleConfig);
    }

    @Bean(name = "shardSqlSessionFactory")
    public SqlSessionFactory shardSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(getDataSource());
        org.apache.ibatis.session.Configuration configuration = sessionFactoryBean.getObject().getConfiguration();
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "mapper/*.xml";
        System.err.println(" >>>>  "+packageSearchPath);
        sessionFactoryBean.setMapperLocations(resourcePatternResolver.getResource(packageSearchPath));
        configuration.setMapUnderscoreToCamelCase(true);//驼峰命名
        return sessionFactoryBean.getObject();

    }
    @Bean(name = "shardTransactionManager")
    public DataSourceTransactionManager shardTransactionManager() throws Exception {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean(name = "shardSqlSessionTemplate")
    public SqlSessionTemplate shardSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(shardSqlSessionFactory());
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("worker", "1");
        return properties;
    }
}
