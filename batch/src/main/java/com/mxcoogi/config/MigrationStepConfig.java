package com.mxcoogi.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
public class MigrationStepConfig {

    @Bean
    public Step migrationStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<Map<String, Object>> migrationItemReader,
            ItemProcessor<Map<String, Object>, Map<String, Object>> migrationItemProcessor,
            ItemWriter<Map<String, Object>> migrationItemWriter,
            StepExecutionListener migrationStepListener) {

        return new StepBuilder("migrationStep", jobRepository)
                .<Map<String, Object>, Map<String, Object>>chunk(100, transactionManager)
                .reader(migrationItemReader)
                .processor(migrationItemProcessor)
                .writer(migrationItemWriter)
                .listener(migrationStepListener)
                .build();

    }
}
