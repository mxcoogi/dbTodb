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
            String url,
            String driverClassVersion) {
        try {
            HikariDataSource ds = new HikariDataSource();
            ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
            ds.setJdbcUrl(url);
            ds.setUsername(username);
            ds.setPassword(password);
            return ds;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
