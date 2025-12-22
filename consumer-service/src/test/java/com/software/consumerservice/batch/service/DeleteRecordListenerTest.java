package com.software.consumerservice.batch.service;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class DeleteRecordListenerTest {

    @Test
    void beforeJobCallsStepExecutionsAndJobInstance() {
        DeleteRecordListener listener = new DeleteRecordListener();

        JobExecution jobExecution = mock(JobExecution.class);
        JobInstance jobInstance = mock(JobInstance.class);
        when(jobExecution.getStepExecutions()).thenReturn(Collections.emptySet());
        when(jobExecution.getJobInstance()).thenReturn(jobInstance);
        when(jobInstance.getJobName()).thenReturn("testJob");

        assertDoesNotThrow(() -> listener.beforeJob(jobExecution));

        verify(jobExecution).getStepExecutions();
        verify(jobExecution).getJobInstance();
        verify(jobInstance).getJobName();
    }

    @Test
    void afterJobHandlesCompletedAndFailedStatuses() {
        DeleteRecordListener listener = new DeleteRecordListener();

        JobExecution jobExecution = mock(JobExecution.class);
        JobInstance jobInstance = mock(JobInstance.class);
        when(jobExecution.getJobInstance()).thenReturn(jobInstance);
        when(jobInstance.getJobName()).thenReturn("testJob");

        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED, BatchStatus.FAILED);
        assertDoesNotThrow(() -> listener.afterJob(jobExecution));
        assertDoesNotThrow(() -> listener.afterJob(jobExecution));
    }
}
