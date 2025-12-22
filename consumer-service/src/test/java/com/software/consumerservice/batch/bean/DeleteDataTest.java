package com.software.consumerservice.batch.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteDataTest {

    @Test
    void lombokGeneratedMethodsWork() {
        DeleteData d = new DeleteData("1", "msg");
        assertEquals("1", d.getId());
        assertEquals("msg", d.getMessage());

        d.setId("2");
        d.setMessage("other");
        assertEquals("2", d.getId());
        assertEquals("other", d.getMessage());

        DeleteData d2 = new DeleteData("2", "other");
        assertEquals(d2, d);
        assertTrue(d.toString().contains("other"));
    }
}

