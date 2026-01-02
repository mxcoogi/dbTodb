package com.mxcoogi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class MigrationStepListener implements StepExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(MigrationStepListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Step Execution Started");
        StepExecutionListener.super.beforeStep(stepExecution);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Step Execution Ended");
        return StepExecutionListener.super.afterStep(stepExecution);
    }
}
