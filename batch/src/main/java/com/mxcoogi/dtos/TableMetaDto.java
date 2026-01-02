package com.mxcoogi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableMetaDto {
    private String schema;
    private String tableName;

    private List<ColumnMetaDto> columns;
    private List<PrimaryKeyDto> primaryKeys;
    private List<ForeignKeyDto> foreignKeys;
    private List<IndexMetaDto> indexes;
}
