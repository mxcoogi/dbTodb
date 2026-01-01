package com.mxcoogi.services;

import com.mxcoogi.components.DataSourceFactory;
import com.mxcoogi.dtos.ConnectionDto;
import com.mxcoogi.eums.DatabaseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatasourceConnectionTestService {
    private final DataSourceFactory dataSourceFactory;

    public void testConnection(ConnectionDto request){
        DataSource sourceDataSource = dataSourceFactory.create(
                DatabaseType.valueOf(request.getSourceType()),
                request.getSourceUrl(),
                request.getSourceUsername(),
                request.getSourcePassword());
        try {
            Connection connection = sourceDataSource.getConnection();
            log.info("Connection established : {}", request.getSourceUrl());
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DataSource targetDataSource = dataSourceFactory.create(
                DatabaseType.valueOf(request.getTargetType()),
                request.getTargetUrl(),
                request.getTargetUsername(),
                request.getTargetPassword());
        try {
            Connection connection = targetDataSource.getConnection();
            log.info("Connection established : {}", request.getTargetUrl());
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
