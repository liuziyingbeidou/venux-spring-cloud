package org.venux.conf;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class ActivitiDatadourceConfig extends AbstractProcessEngineAutoConfiguration {

    @Resource
    private ActivitiDataSourceProperties activitiDataSourceProperties;

    @Bean
    @Primary
    public DataSource activitiDataSource() {
        DruidDataSource druiddataSource = new DruidDataSource();
        druiddataSource.setUrl(activitiDataSourceProperties.getUrl());
        druiddataSource.setDriverClassName(activitiDataSourceProperties.getDriverClassName());
        druiddataSource.setPassword(activitiDataSourceProperties.getPassword());
        druiddataSource.setUsername(activitiDataSourceProperties.getUsername());
        return druiddataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(activitiDataSource());
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(activitiDataSource());
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setJobExecutorActivate(true);
        configuration.setTransactionManager(transactionManager());
        return configuration;
    }
}