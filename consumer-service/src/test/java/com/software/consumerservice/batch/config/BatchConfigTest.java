package com.software.consumerservice.batch.config;

import com.software.consumerservice.batch.constants.Constants;
import com.software.consumerservice.batch.service.DeleteRecordListener;
import com.software.consumerservice.batch.service.DeleteRecordProcessor;
import com.software.consumerservice.batch.service.DeleteRecordReader;
import com.software.consumerservice.batch.service.DeleteRecordWriter;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BatchConfigTest {

    @Test
    void deleteRecordsReturnsStepWithConfiguredName() {
        DeleteRecordReader reader = mock(DeleteRecordReader.class);
        DeleteRecordWriter writer = mock(DeleteRecordWriter.class);
        DeleteRecordProcessor processor = mock(DeleteRecordProcessor.class);
        JobRepository jobRepository = mock(JobRepository.class);
        PlatformTransactionManager tx = mock(PlatformTransactionManager.class);
        DeleteRecordListener listener = mock(DeleteRecordListener.class);

        BatchConfig cfg = new BatchConfig(reader, writer, processor, jobRepository, tx, listener);

        Step step = cfg.deleteRecords();

        assertNotNull(step);
        assertEquals(Constants.DELETE_RECORDS_STEP, step.getName());
    }

    @Test
    void deleteRecordJobCreatesJobWithConfiguredNameAndNonNull() {
        DeleteRecordReader reader = mock(DeleteRecordReader.class);
        DeleteRecordWriter writer = mock(DeleteRecordWriter.class);
        DeleteRecordProcessor processor = mock(DeleteRecordProcessor.class);
        JobRepository jobRepository = mock(JobRepository.class);
        PlatformTransactionManager tx = mock(PlatformTransactionManager.class);
        DeleteRecordListener listener = mock(DeleteRecordListener.class);

        BatchConfig cfg = new BatchConfig(reader, writer, processor, jobRepository, tx, listener);

        Job job = cfg.deleteRecordJob();

        assertNotNull(job);
        assertEquals(Constants.DELETE_RECORDS_JOB, job.getName());
    }

    @Test
    void callingDeleteRecordsMultipleTimesReturnsStepsWithSameName() {
        DeleteRecordReader reader = mock(DeleteRecordReader.class);
        DeleteRecordWriter writer = mock(DeleteRecordWriter.class);
        DeleteRecordProcessor processor = mock(DeleteRecordProcessor.class);
        JobRepository jobRepository = mock(JobRepository.class);
        PlatformTransactionManager tx = mock(PlatformTransactionManager.class);
        DeleteRecordListener listener = mock(DeleteRecordListener.class);

        BatchConfig cfg = new BatchConfig(reader, writer, processor, jobRepository, tx, listener);

        Step step1 = cfg.deleteRecords();
        Step step2 = cfg.deleteRecords();

        assertNotNull(step1);
        assertNotNull(step2);
        assertEquals(Constants.DELETE_RECORDS_STEP, step1.getName());
        assertEquals(Constants.DELETE_RECORDS_STEP, step2.getName());
    }
}

