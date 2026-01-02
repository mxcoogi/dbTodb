package com.mxcoogi.config;

import com.mxcoogi.components.DatasourceFactory;
import com.mxcoogi.components.JsonUtilComponent;
import com.mxcoogi.dtos.ConnectionDto;
import com.mxcoogi.dtos.MappingDto;
import com.mxcoogi.enums.DatabaseType;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class MigrationItemWriterConfig {

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Map<String, Object>> migrationItemWriter(
            DatasourceFactory datasourceFactory,
            @Value("#{jobParameters['connection']}") String connectionJson,
            @Value("#{jobParameters['mapping']}") String mappingJson,
            JsonUtilComponent jsonUtil
    ) {
        ConnectionDto connection = jsonUtil.toConnection(connectionJson);
        List<MappingDto> mappings = jsonUtil.toMappings(mappingJson);

        System.out.println(connection);
        System.out.println(mappings);



        return null;
    }
}
