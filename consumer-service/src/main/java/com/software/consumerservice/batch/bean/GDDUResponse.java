package com.software.consumerservice.batch.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GDDUResponse {
    private List<DeleteData> content;
    private boolean isLast;
    private int totalPages;
    private int totalElements;
    private int numberOfElements;
    private int pageNumber;
}
