package com.mxcoogi.config;

import com.mxcoogi.dtos.MappingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class MigrationProcessorConfig {


    @Bean
    @StepScope
    public ItemProcessor<Map<String, Object>, Map<String, Object>> migrationItemProcessor() {
        return item -> {
            log.info("📦 Processor received row = {}", item);
            return item;
        };
    }

}
