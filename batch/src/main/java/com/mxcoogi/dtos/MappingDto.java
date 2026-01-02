package com.mxcoogi.dtos;

import lombok.Data;

@Data
public class MappingDto {
    private String sourceTable;
    private String sourceColumn;
    private String targetTable;
    private String targetColumn;
}
