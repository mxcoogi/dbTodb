package com.mxcoogi.components;


import java.sql.Connection;

public interface DatabaseConnector {

    Connection createConnection(
            String username,
            String password,
            String url
    );

    String getName();
}
