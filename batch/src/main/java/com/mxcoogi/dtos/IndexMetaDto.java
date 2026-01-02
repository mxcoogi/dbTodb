package com.mxcoogi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexMetaDto {
    private String indexName;
    private String columnName;
    private Boolean unique;
    private Short ordinalPosition;
}
