package com.mxcoogi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@Slf4j
public class MigrationItemWriterConfig {

    @Bean
//    @StepScope
    public ItemWriter<Map<String, Object>> migrationItemWriter() {
        return items -> {
            for (Map<String, Object> item : items) {
                log.info("📝 Writer received = {}", item);
            }
        };
    }
}
