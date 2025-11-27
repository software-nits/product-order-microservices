package com.software.consumerservice.batch.service;

import com.software.consumerservice.batch.bean.DeleteData;
import com.software.consumerservice.batch.bean.GDDUResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@StepScope
public class DeleteRecordReader implements ItemReader<DeleteData> {
    private final Logger logger = LoggerFactory.getLogger(DeleteRecordReader.class);

    @Autowired
    private DeleteRecordService deleteRecordService;

    private GDDUResponse deleteData;
    private boolean hasNext;
    private boolean firstRequestSent;
    private int nextPage;
    private boolean pageable;
//    private int nextSize;
//    private Long size = 0L;
//    private String jobBatchId;
//    private String jobStartTime;
//    private String recordClass;
//    private String controlNumber;
//    private String batchId;
    private int count=0;

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
            nextDelete = deleteData.getContent().remove(0);
        }
        return nextDelete;
    }
    private boolean canSendRequest(){
        return firstRequestSent || (pageable && this.deleteData.getContent().isEmpty() && this.hasNext);
    }
}
