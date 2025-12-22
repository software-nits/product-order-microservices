package com.software.consumerservice.batch.service;

import com.software.consumerservice.batch.bean.DeleteData;
import com.software.consumerservice.batch.bean.GDDUResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Service;

@Service
@StepScope
public class DeleteRecordReader implements ItemReader<DeleteData> {
    private final Logger logger = LoggerFactory.getLogger(DeleteRecordReader.class);

    private final DeleteRecordService deleteRecordService;

    private GDDUResponse deleteData;
    private boolean hasNext;
    private boolean firstRequestSent;
    private int nextPage;
    private boolean pageable;
    private int count=0;

    public DeleteRecordReader(DeleteRecordService deleteRecordService) {
        this.deleteRecordService = deleteRecordService;
    }

    @Override
    public DeleteData read() {
        logger.info("Reading {} data from DB", count++);
        if (canSendRequest()){
            deleteData = deleteRecordService.fetchData();
            if (deleteData != null){
                hasNext = !deleteData.isLast();
            }
            nextPage++;
        }
        DeleteData nextDelete = null;
        if (deleteData != null && !deleteData.getContent().isEmpty()) {
            nextDelete = deleteData.getContent().removeFirst();
        }
        return nextDelete;
    }
    private boolean canSendRequest(){
        return firstRequestSent || (pageable && this.deleteData.getContent().isEmpty() && this.hasNext);
    }
}
