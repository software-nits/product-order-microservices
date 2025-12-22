package com.software.consumerservice.batch.service;

import com.software.consumerservice.batch.bean.DeleteData;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;

import java.util.List;

class DeleteRecordWriterTest {

    @Test
    void writeLogsItems() {
        DeleteRecordWriter writer = new DeleteRecordWriter();
        Chunk<DeleteData> chunk = new Chunk<>(List.of(new DeleteData("1","a"), new DeleteData("2","b")));
        writer.write(chunk);
        // no assertion - ensure no exception and method is callable
    }
}
