package com.mxcoogi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForeignKeyDto {
    private String fkName;

    private String fkColumn;   // 현재 테이블 컬럼
    private String pkTable;    // 참조 테이블
    private String pkColumn;   // 참조 컬럼

    private Short keySeq;      // 복합 FK 순서
}