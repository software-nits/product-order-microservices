package com.software.consumerservice.batch.config;

import com.software.consumerservice.batch.bean.DeleteData;
import com.software.consumerservice.batch.constants.Constants;
import com.software.consumerservice.batch.service.DeleteRecordListener;
import com.software.consumerservice.batch.service.DeleteRecordProcessor;
import com.software.consumerservice.batch.service.DeleteRecordReader;
import com.software.consumerservice.batch.service.DeleteRecordWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfiguration {
    @Autowired
    private DeleteRecordReader deleteRecordReader;
    @Autowired
    private DeleteRecordWriter deleteRecordWriter;
    @Autowired
    private DeleteRecordProcessor deleteRecordProcessor;
    @Autowired
    private JobBuilder jobBuilder;
    @Autowired
    private StepBuilder stepBuilder;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    private DeleteRecordListener deleteRecordListener;

    @Bean
    @Primary
    public Step deleteRecords() {
        return new StepBuilder("processPeopleStep", jobRepository).<DeleteData,DeleteData>chunk(10, transactionManager)
                .reader(deleteRecordReader)
                .processor(deleteRecordProcessor)
                .writer(deleteRecordWriter)
                .faultTolerant()
                .build();
    }

    @Bean(name = Constants.DELETE_RECORDS_JOB)
    @Primary
    public Job deleteRecordJob() {
        return jobBuilder.incrementer(new RunIdIncrementer())
                .listener(deleteRecordListener)
                .start(deleteRecords()).build();

    }
}
