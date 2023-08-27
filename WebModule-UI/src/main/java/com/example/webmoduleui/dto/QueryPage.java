package com.example.webmoduleui.dto;

import lombok.Data;

@Data
public class QueryPage {
    private String selectName;
    private Long pageNumber;
    private Long pageSize;
}
