package com.software.consumerservice.batch.service;

import com.software.consumerservice.batch.bean.GDDUResponse;
import com.software.consumerservice.batch.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.JobInstance;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DeleteRecordService {
    private final Logger logger = LoggerFactory.getLogger(DeleteRecordService.class);

    @Autowired
    private JobOperator jobOperator;
    @Autowired
    private JobRepository jobRepository;

//    @Qualifier(Constants.DELETE_RECORDS_JOB)
    private Job job;

    public boolean isJobRunning() {
        JobInstance jobInstance = jobRepository.getLastJobInstance(Constants.DELETE_RECORDS_JOB);
        boolean isRunning = false;
        if (jobInstance != null) {
            JobExecution jobExecution = jobRepository.getJobExecution(jobInstance.getId());
            assert jobExecution != null;
            isRunning = jobExecution.isRunning();
            logger.info("job status {}", isRunning);
        }
        return isRunning;
    }

    public void startLoadJob() throws JobInstanceAlreadyCompleteException, InvalidJobParametersException, JobExecutionAlreadyRunningException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("start date", new Date())
                .toJobParameters();
        startJob(jobParameters);
    }

    private void startJob(JobParameters jobParameters) throws JobInstanceAlreadyCompleteException, InvalidJobParametersException, JobExecutionAlreadyRunningException, JobRestartException {
        JobExecution execution = jobOperator.start(job, jobParameters);
        ExitStatus exitStatus = execution.getExitStatus();
        logger.info("status {}",exitStatus.getExitDescription());
        if(ExitStatus.COMPLETED.getExitCode().equals(exitStatus.getExitCode())) {
            logger.info("Job completed successfully {}",exitStatus.getExitDescription());
        }else{
            List<Throwable> errors = execution.getAllFailureExceptions();
            errors.forEach((error)->logger.info("Exception found in startJob() method: {}",error.getMessage()));
        }
    }

    public GDDUResponse fetchData() {
        return new GDDUResponse();
    }
}
