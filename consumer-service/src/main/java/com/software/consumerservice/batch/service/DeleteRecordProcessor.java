package com.software.consumerservice.batch.service;

import com.software.consumerservice.batch.bean.DeleteData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class DeleteRecordProcessor implements ItemProcessor<DeleteData, DeleteData> {
    private final Logger logger = LoggerFactory.getLogger(DeleteRecordProcessor.class);
    private int count=0;
    @Override
    public DeleteData process(DeleteData deleteData) {
        logger.info("Processing {} data from DB", count++);
        return deleteData;
    }
}
