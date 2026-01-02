package com.mxcoogi.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class MigrationJobListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(MigrationJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job Started");
        JobExecutionListener.super.beforeJob(jobExecution);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Job Ended");
        JobExecutionListener.super.afterJob(jobExecution);
    }
}
