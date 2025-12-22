package com.software.consumerservice.batch.service;

import com.software.consumerservice.batch.bean.DeleteData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteRecordProcessorTest {

    @Test
    void processReturnsSameInstance() {
        DeleteRecordProcessor processor = new DeleteRecordProcessor();
        DeleteData in = new DeleteData("1","m");
        DeleteData out = processor.process(in);
        assertSame(in, out);
    }
}
