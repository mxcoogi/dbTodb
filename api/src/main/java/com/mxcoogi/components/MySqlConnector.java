package com.mxcoogi.components;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class MySqlConnector implements DatabaseConnector {
    @Override
    public Connection createConnection(
            String username,
            String password,
            String url) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return "MYSQL";
    }
}
