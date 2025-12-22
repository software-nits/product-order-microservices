package com.software.consumerservice.batch.service;

import com.software.consumerservice.batch.bean.DeleteData;
import com.software.consumerservice.batch.bean.GDDUResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteRecordReaderTest {

    @Test
    void readRequestsFromServiceAndReturnsItemsUntilEmpty() {
        DeleteRecordService svc = mock(DeleteRecordService.class);
        // prepare a response with two items and isLast true
        GDDUResponse resp = new GDDUResponse(new java.util.LinkedList<>(java.util.List.of(new DeleteData("1","a"), new DeleteData("2","b"))), true,1,2,2,0);
        when(svc.fetchData()).thenReturn(resp);

        DeleteRecordReader reader = new DeleteRecordReader(svc);

        DeleteData d1 = reader.read();
        assertNotNull(d1);
        assertEquals("1", d1.getId());

        DeleteData d2 = reader.read();
        assertNotNull(d2);
        assertEquals("2", d2.getId());

        // after content empty and isLast true, next read should return null
        DeleteData d3 = reader.read();
        assertNull(d3);

        verify(svc, atLeast(1)).fetchData();
    }
}
