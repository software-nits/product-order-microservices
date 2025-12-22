package com.software.consumerservice.batch.bean;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GDDUResponseTest {

    @Test
    void gettersAndSetters() {
        DeleteData d = new DeleteData("1","m");
        GDDUResponse r = new GDDUResponse(List.of(d), true, 1,1,1,0);
        assertEquals(1, r.getContent().size());
        assertTrue(r.isLast());
        assertEquals(1, r.getTotalPages());
        assertEquals(1, r.getTotalElements());
        assertEquals(1, r.getNumberOfElements());
        assertEquals(0, r.getPageNumber());

        r.setLast(false);
        assertFalse(r.isLast());
    }
}

