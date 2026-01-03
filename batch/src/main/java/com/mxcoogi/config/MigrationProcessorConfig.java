package com.mxcoogi.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxcoogi.dtos.MappingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class MigrationProcessorConfig {


    @Bean
    @StepScope
    public ItemProcessor<Map<String, Object>, Map<String, Object>> migrationItemProcessor(
            ObjectMapper objectMapper,
            @Value("#{jobParameters['mapping']}") String encodedMapping
    ) {
        List<MappingDto> mappings;
        String mappingJson = new String(Base64.getDecoder().decode(encodedMapping));
        log.info("Decoded mapping JSON: {}", mappingJson);
        try {
            mappings = objectMapper.readValue(mappingJson,
                    new TypeReference<List<MappingDto>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return item -> {
            HashMap<String, Object> map = new HashMap<>();
            for(MappingDto mapping : mappings) {
                String sourceColumn = mapping.getSourceColumn();
                String targetColumn = mapping.getTargetColumn();
                log.info("Mapping {} -> {}", sourceColumn, targetColumn);
                map.put(targetColumn, item.get(sourceColumn));
            }
            log.info("📦 Processor received row = {}", map);
            return map;
        };
    }

}
