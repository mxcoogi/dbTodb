package com.mxcoogi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ColumnMetaDto {
    private String name;          // 컬럼명
    private String type;          // DB 원본 타입 (VARCHAR, NUMBER, TEXT 등)
    private Integer size;         // 길이
    private Boolean nullable;     // NULL 가능 여부
    private String defaultValue;  // DEFAULT 값
    private Integer position;     // 컬럼 순서
}
