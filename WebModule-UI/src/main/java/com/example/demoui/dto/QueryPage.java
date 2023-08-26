package com.example.demoui.dto;

import lombok.Data;

@Data
public class QueryPage {
    private String selectName;
    private Long pageNumber;
    private Long pageSize;
}
