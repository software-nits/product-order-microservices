package com.software.consumerservice.batch.service;

import com.software.consumerservice.batch.bean.GDDUResponse;
import com.software.consumerservice.batch.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class DeleteRecordService {
    private final Logger logger = LoggerFactory.getLogger(DeleteRecordService.class);

    private final JobOperator jobOperator;
    private final JobRepository jobRepository;

    @Qualifier(Constants.DELETE_RECORDS_JOB)
    private Job job;

    public DeleteRecordService(JobOperator jobOperator, JobRepository jobRepository) {
        this.jobOperator = jobOperator;
        this.jobRepository = jobRepository;
    }

    public boolean isJobRunning() {
        JobParameters jobParameters = new JobParameters();
        JobInstance jobInstance = jobRepository.getJobInstance(Constants.DELETE_RECORDS_JOB, jobParameters);
        boolean isRunning = false;
        if (jobInstance != null) {
            List<JobExecution> jobExecution = jobRepository.findJobExecutions(jobInstance);
            isRunning = jobExecution.getFirst().isRunning();
            logger.info("job status {}", isRunning);
        }
        return isRunning;
    }

    public void startLoadJob() throws JobInstanceAlreadyExistsException, NoSuchJobException, JobParametersInvalidException {
        Properties jobParameters = new Properties();
        jobParameters.putIfAbsent("start date", new Date().toString());
        startJob(jobParameters);
    }

    private void startJob(Properties jobParameters) throws JobInstanceAlreadyExistsException, NoSuchJobException, JobParametersInvalidException {
        Long execution = jobOperator.start(Constants.DELETE_RECORDS_JOB, jobParameters);
        int exitStatus = execution.intValue();
//        logger.info("status {}",exitStatus.getExitDescription());
//        if(ExitStatus.COMPLETED.getExitCode().equals(exitStatus.getExitCode())) {
//            logger.info("Job completed successfully {}",exitStatus.getExitDescription());
//        }else{
//            List<Throwable> errors = execution.getAllFailureExceptions();
//            errors.forEach((error)->logger.info("Exception found in startJob() method: {}",error.getMessage()));
//        }
    }

    public GDDUResponse fetchData() {
        return new GDDUResponse();
    }
}
