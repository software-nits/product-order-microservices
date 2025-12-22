package com.software.consumerservice.batch.service;

import com.software.consumerservice.batch.bean.GDDUResponse;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteRecordServiceTest {

    @Test
    void fetchDataReturnsGDDUResponse() {
        JobOperator jobOperator = mock(JobOperator.class);
        JobRepository jobRepository = mock(JobRepository.class);
        DeleteRecordService service = new DeleteRecordService(jobOperator, jobRepository);

        GDDUResponse r1 = service.fetchData();
        assertNotNull(r1);
        assertEquals(20, r1.getContent().size());
        // second call should set isLast = false because of internal counter
        GDDUResponse r2 = service.fetchData();
        assertNotNull(r2);
    }

    @Test
    void isJobRunningChecksRepository() {
        JobOperator jobOperator = mock(JobOperator.class);
        JobRepository jobRepository = mock(JobRepository.class);
        DeleteRecordService service = new DeleteRecordService(jobOperator, jobRepository);

        when(jobRepository.getJobInstance(anyString(), any(JobParameters.class))).thenReturn(null);
        assertFalse(service.isJobRunning());

        // simulate running job
        JobInstance instance = mock(JobInstance.class);
        when(jobRepository.getJobInstance(anyString(), any(JobParameters.class))).thenReturn(instance);
        List<JobExecution> executions = new ArrayList<>();
        JobExecution exec = mock(JobExecution.class);
        when(exec.isRunning()).thenReturn(true);
        executions.add(exec);
        when(jobRepository.findJobExecutions(instance)).thenReturn(executions);
        assertTrue(service.isJobRunning());
    }
}
