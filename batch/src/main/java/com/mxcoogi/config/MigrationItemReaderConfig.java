package com.mxcoogi.config;

import com.mxcoogi.components.DatasourceFactory;
import com.mxcoogi.components.JsonUtilComponent;
import com.mxcoogi.dtos.ConnectionDto;
import com.mxcoogi.dtos.MappingDto;
import com.mxcoogi.enums.DatabaseType;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.ColumnMapRowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Configuration
public class MigrationItemReaderConfig {
    @Bean
    @StepScope
    public JdbcCursorItemReader<Map<String, Object>> migrationItemReader(
            DatasourceFactory datasourceFactory,
            @Value("#{jobParameters['connection']}") String connectionJson,
            @Value("#{jobParameters['mapping']}") String mappingJson,
            JsonUtilComponent jsonUtil
    ) {
        ConnectionDto connection = jsonUtil.toConnection(connectionJson);
        List<MappingDto> mappings = jsonUtil.toMappings(mappingJson);

        DataSource sourceDs = datasourceFactory.create(
                DatabaseType.valueOf(connection.getSourceType()),
                connection.getSourceUrl(),
                connection.getSourceUsername(),
                connection.getSourcePassword()
        );

        String table = mappings.get(0).getSourceTable();
        String sql = "SELECT * FROM " + table;

        JdbcCursorItemReader<Map<String, Object>> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(sourceDs);
        reader.setSql(sql);
        reader.setRowMapper(new ColumnMapRowMapper());
        return reader;
    }
}
