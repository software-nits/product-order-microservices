package com.software.consumerservice.batch.controller;

import com.software.consumerservice.batch.service.DeleteRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class BatchController {
    private final Logger logger = LoggerFactory.getLogger(BatchController.class);
    @Autowired
    private DeleteRecordService deleteRecordService;

    @GetMapping("/delete-records")
    public ResponseEntity<String> deleteRecord() throws JobInstanceAlreadyCompleteException, InvalidJobParametersException, JobExecutionAlreadyRunningException, JobRestartException {
        String jobStatus;
        if (deleteRecordService.isJobRunning()){
            jobStatus = "Job is already running, please try again later.";
            logger.info("Job is already running, please try again later.");
        }else {
            jobStatus =  "Job is not running, please start new job.";
//            jobStatus = jobStatusBuilder();
            deleteRecordService.startLoadJob();
        }
        return new ResponseEntity<>(jobStatus, HttpStatus.OK);
    }
}
