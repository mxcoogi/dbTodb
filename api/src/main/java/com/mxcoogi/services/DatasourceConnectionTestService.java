package com.mxcoogi.services;

import com.mxcoogi.components.DataSourceFactory;
import com.mxcoogi.dtos.ConnectionDto;
import com.mxcoogi.dtos.TableInfoDto;
import com.mxcoogi.eums.DatabaseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatasourceConnectionTestService {
    private final DataSourceFactory dataSourceFactory;

    public Object testConnection(ConnectionDto request){

        Map<String,Object> map = new HashMap<>();

        DataSource sourceDataSource = dataSourceFactory.create(
                DatabaseType.valueOf(request.getSourceType()),
                request.getSourceUrl(),
                request.getSourceUsername(),
                request.getSourcePassword());
        try {
            Connection connection = sourceDataSource.getConnection();
            log.info("Connection established : {}", request.getSourceUrl());
            List<TableInfoDto> sourceTable = this.getTables(sourceDataSource);
            map.put("sourceTable", sourceTable);
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
            List<TableInfoDto> targetTable = this.getTables(targetDataSource);
            map.put("targetTable", targetTable);
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
    public List<TableInfoDto> getTables(DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();

            ResultSet rs = meta.getTables(
                    conn.getCatalog(),
                    null,
                    "%",
                    new String[]{"TABLE"}
            );

            List<TableInfoDto> tables = new ArrayList<>();


            while (rs.next()) {
                String catalog = rs.getString("TABLE_CAT");
                String schema = rs.getString("TABLE_SCHEM");
                String dbName = catalog != null ? catalog : schema;
                tables.add(new TableInfoDto(
                        dbName,
                        rs.getString("TABLE_NAME"),
                        rs.getString("TABLE_TYPE")
                ));
            }

            return tables;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load tables", e);
        }
    }

}
