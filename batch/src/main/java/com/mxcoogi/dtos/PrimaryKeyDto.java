package com.mxcoogi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrimaryKeyDto {

    private String columnName;
    private Short keySeq;     // 복합 PK 순서
    private String pkName;
}
