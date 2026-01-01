package com.mxcoogi.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BatchInfo {
    ConnectionDto connectionDto;
    List<MappingDto> mappingDto;
}
