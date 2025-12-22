package com.software.consumerservice.batch.config;

import com.software.consumerservice.batch.bean.DeleteData;
import com.software.consumerservice.batch.constants.Constants;
import com.software.consumerservice.batch.service.DeleteRecordListener;
import com.software.consumerservice.batch.service.DeleteRecordProcessor;
import com.software.consumerservice.batch.service.DeleteRecordReader;
import com.software.consumerservice.batch.service.DeleteRecordWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig{
    private final DeleteRecordReader deleteRecordReader;
    private final DeleteRecordWriter deleteRecordWriter;
    private final DeleteRecordProcessor deleteRecordProcessor;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DeleteRecordListener deleteRecordListener;

    public BatchConfig(DeleteRecordReader deleteRecordReader, DeleteRecordWriter deleteRecordWriter, DeleteRecordProcessor deleteRecordProcessor, JobRepository jobRepository, PlatformTransactionManager transactionManager, DeleteRecordListener deleteRecordListener) {
        this.deleteRecordReader = deleteRecordReader;
        this.deleteRecordWriter = deleteRecordWriter;
        this.deleteRecordProcessor = deleteRecordProcessor;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.deleteRecordListener = deleteRecordListener;
    }

    @Bean
    @Primary
    public Step deleteRecords() {
        return new StepBuilder(Constants.DELETE_RECORDS_STEP, jobRepository).<DeleteData,DeleteData>chunk(10, transactionManager)
                .reader(deleteRecordReader)
                .processor(deleteRecordProcessor)
                .writer(deleteRecordWriter)
                .faultTolerant()
                .build();
    }

    @Bean(name = Constants.DELETE_RECORDS_JOB)
    @Primary
    public Job deleteRecordJob() {
        return new JobBuilder(Constants.DELETE_RECORDS_JOB, jobRepository).incrementer(new RunIdIncrementer())
                .listener(deleteRecordListener)
                .start(deleteRecords()).build();

    }
}
