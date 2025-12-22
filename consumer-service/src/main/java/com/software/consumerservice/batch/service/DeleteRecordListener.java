package com.software.consumerservice.batch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Service;

@Service
public class DeleteRecordListener implements JobExecutionListener {
    private final Logger logger = LoggerFactory.getLogger(DeleteRecordListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobExecution.getStepExecutions();
        logger.info("Job {} is starting.", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("Job {} has finished with status: {}", jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("Job has finished with success status: {}", jobExecution.getStatus());
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            logger.info("Job has finished with failed status: {}", jobExecution.getStatus());
        }
    }
}
