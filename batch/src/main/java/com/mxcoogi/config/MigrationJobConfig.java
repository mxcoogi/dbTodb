package com.mxcoogi.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MigrationJobConfig {

    @Bean
    public Job migrationJob(JobRepository jobRepository,
                            Step migrationStep,
                            JobExecutionListener migrationListener) {
        return new JobBuilder("migrationJob", jobRepository)
                .listener(migrationListener)
                .start(migrationStep).build();

    }
}
