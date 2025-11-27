package com.software.consumerservice.batch.service;

import com.software.consumerservice.batch.bean.DeleteData;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class DeleteRecordProcessor implements ItemProcessor<DeleteData, DeleteData> {
    @Override
    public DeleteData process(DeleteData deleteData) {
        return deleteData;
    }
}
