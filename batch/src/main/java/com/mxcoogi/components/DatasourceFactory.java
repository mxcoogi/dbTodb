package com.mxcoogi.components;

import com.mxcoogi.enums.DatabaseType;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DatasourceFactory {

    public DataSource create(DatabaseType type, String url, String user, String pw) {

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(url);
        ds.setUsername(user);
        ds.setPassword(pw);

        switch (type) {
            case MYSQL -> ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
            case POSTGRESQL -> ds.setDriverClassName("org.postgresql.Driver");
            case ORACLE -> ds.setDriverClassName("oracle.jdbc.OracleDriver");
            default -> throw new IllegalArgumentException("Unknown database type: " + type);
        }

        ds.setMaximumPoolSize(5);
        ds.setMinimumIdle(1);
        ds.setAutoCommit(false);

        return ds;
    }
}
