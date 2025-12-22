package com.software.consumerservice.batch.controller;

import com.software.consumerservice.batch.service.DeleteRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class BatchController {
    private final Logger logger = LoggerFactory.getLogger(BatchController.class);
    private final DeleteRecordService deleteRecordService;

    public BatchController(DeleteRecordService deleteRecordService) {
        this.deleteRecordService = deleteRecordService;
    }

    @GetMapping("/delete-records")
    public ResponseEntity<String> deleteRecord() throws JobInstanceAlreadyExistsException, NoSuchJobException, JobParametersInvalidException {
        String jobStatus;
        if (deleteRecordService.isJobRunning()){
            jobStatus = "Job is already running, please try again later.";
            logger.info("Job is already running, please try again later.");
        }else {
            jobStatus =  "Job is not running, please start new job.";
            deleteRecordService.startLoadJob();
        }
        return new ResponseEntity<>(jobStatus, HttpStatus.OK);
    }
}
