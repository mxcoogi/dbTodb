package com.mxcoogi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxcoogi.components.JsonUtilComponent;
import com.mxcoogi.dtos.MappingDto;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MigrationProcessorConfig {


    @Bean
    @StepScope
    public ItemProcessor<Map<String, Object>, Map<String, Object>> migrationItemProcessor(
            @Value("#{jobParameters['mapping']}") String mappingJson,
            JsonUtilComponent jsonUtil
    ) {
        List<MappingDto> mappings = jsonUtil.toMappings(mappingJson);

        System.out.println("mappings: " + mappings);
        return null;
    }
}
