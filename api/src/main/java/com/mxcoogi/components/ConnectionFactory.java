package com.mxcoogi.components;

import com.mxcoogi.eums.DatabaseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class ConnectionFactory {


    public Connection create(DatabaseType type, String url, String username, String password) {
        try {
            switch (type) {
                case MYSQL -> Class.forName("com.mysql.cj.jdbc.Driver");
                case POSTGRESQL -> Class.forName("org.postgresql.Driver");
                case ORACLE -> Class.forName("oracle.jdbc.OracleDriver");
            }

            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException("DB Connection failed", e);
        }
    }
}
