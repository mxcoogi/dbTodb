package com.mxcoogi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class TableInfoDto {
    private String schema;
    private String tableName;
    private String type;
}
