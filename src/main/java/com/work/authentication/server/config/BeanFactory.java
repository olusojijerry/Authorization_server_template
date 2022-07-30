package com.work.authentication.server.config;

import com.work.authentication.server.repository.services.CoreUserServiceImpl;
import com.work.authentication.server.repository.services.CoreUsersService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import javax.sql.DataSource;

@Configuration
public class BeanFactory {
    @Bean
    @ConfigurationProperties("core.service.db")
    @Primary
    public DataSource coreDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        return dataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcTemplate coreDataSourceJdbcTemplate(){
        return new JdbcTemplate(coreDataSource());
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "customTokenEnhancer")
    public TokenEnhancer tokenEnhancer(){
        return new CustomTokenEnhancer();
    }

    @Bean
    public CoreUsersService coreUsersService(){
        return new CoreUserServiceImpl();
    }

}
