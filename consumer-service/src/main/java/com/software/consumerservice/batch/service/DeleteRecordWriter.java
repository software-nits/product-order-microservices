package com.software.consumerservice.batch.service;

import com.software.consumerservice.batch.bean.DeleteData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DeleteRecordWriter implements ItemWriter<DeleteData> {
    private final Logger logger = LoggerFactory.getLogger(DeleteRecordWriter.class);

    @Override
    public void write(Chunk<? extends DeleteData> deleteDataChunk) {
        AtomicInteger count= new AtomicInteger(0);
        deleteDataChunk.forEach(item -> logger.info("{}: item with value: {}", count.getAndIncrement(),item.toString()));
        logger.info("total number of item processed is: {}", count.get());
    }
}
