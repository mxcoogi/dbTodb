package com.mxcoogi.components;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
@Component
public class MySqlConnector implements DatabaseConnector {
    @Override
    public DataSource createDataSource(
            String username,
            String password,
            String url) {
        try {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return "MYSQL";
    }
}
