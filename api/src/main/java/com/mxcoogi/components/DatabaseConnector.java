package com.mxcoogi.components;

import javax.sql.DataSource;
import java.sql.Connection;

public interface DatabaseConnector {

    Connection createDataSource(
            String username,
            String password,
            String url
    );

    String getName();
}
