package com.software.consumerservice.batch.bean;

import lombok.Data;

import java.util.List;

@Data
public class GDDUResponse {
    private List<DeleteData> content;
    private boolean isLast;
    private int totalPages;
    private int totalElements;
    private int numberOfElements;
    private int pageNumber;
}
