package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author mulj
 * @date 2020/8/7 16:38
 * @Email:mlj@citycloud.com.cn
 */
@Configuration
public class DataSourceConfig {
    @Primary
    @Bean(destroyMethod = "close", initMethod = "init", name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSourceTwo() {
        return new DruidDataSource();
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplateTwo(
            @Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
