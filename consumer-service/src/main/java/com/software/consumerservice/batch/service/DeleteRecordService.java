package com.software.consumerservice.batch.service;

import com.software.consumerservice.batch.bean.DeleteData;
import com.software.consumerservice.batch.bean.GDDUResponse;
import com.software.consumerservice.batch.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class DeleteRecordService {
    private final Logger logger = LoggerFactory.getLogger(DeleteRecordService.class);
    private int totalPage =0;

    private final JobOperator jobOperator;
    private final JobRepository jobRepository;

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

    public void startJob() throws JobInstanceAlreadyExistsException, NoSuchJobException, JobParametersInvalidException {
        Properties jobParameters = new Properties();
        jobParameters.putIfAbsent("start date", new Date().toString());
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
       List<DeleteData> deleteDataList = new ArrayList<>();
       deleteDataList.add( new DeleteData("1","Record1"));
       deleteDataList.add( new DeleteData("2","Record2"));
       deleteDataList.add( new DeleteData("3","Record3"));
       deleteDataList.add( new DeleteData("4","Record4"));
       deleteDataList.add( new DeleteData("5","Record5"));
       deleteDataList.add( new DeleteData("6","Record6"));
       deleteDataList.add( new DeleteData("7","Record7"));
       deleteDataList.add( new DeleteData("8","Record8"));
       deleteDataList.add( new DeleteData("9","Record9"));
       deleteDataList.add( new DeleteData("10","Record10"));
       deleteDataList.add( new DeleteData("11","Record11"));
       deleteDataList.add( new DeleteData("12","Record12"));
       deleteDataList.add( new DeleteData("13","Record13"));
       deleteDataList.add( new DeleteData("14","Record14"));
       deleteDataList.add( new DeleteData("15","Record15"));
       deleteDataList.add( new DeleteData("16","Record16"));
       deleteDataList.add( new DeleteData("17","Record17"));
       deleteDataList.add( new DeleteData("18","Record18"));
       deleteDataList.add( new DeleteData("19","Record19"));
       deleteDataList.add( new DeleteData("20","Record20"));
       return totalPage++>1?new GDDUResponse(deleteDataList, false, 30, 1, 30, 0): new GDDUResponse(deleteDataList, true, 30, 1, 30, 0);
    }
}
