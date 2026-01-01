package com.mxcoogi.components;

import com.mxcoogi.eums.DatabaseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DataSourceFactory {

    private final Map<String, DatabaseConnector> connectorMap;

    public DataSourceFactory(List<DatabaseConnector> connectors) {
        this.connectorMap = connectors.stream()
                .collect(Collectors.toMap(
                        DatabaseConnector::getName,
                        Function.identity()
                ));
    }

    public DataSource create(DatabaseType type, String url, String username, String password) {
        DatabaseConnector connector = connectorMap.get(type.name());

        if (connector == null) {
            throw new IllegalArgumentException("Unsupported DB type");
        }
        return connector.createDataSource(
                username,
                password,
                url
        );
    }
}
