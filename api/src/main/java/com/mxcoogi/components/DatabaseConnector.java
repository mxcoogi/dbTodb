package com.mxcoogi.components;

import javax.sql.DataSource;

public interface DatabaseConnector {

    DataSource createDataSource(
            String username,
            String password,
            String url
    );

    String getName();
}
