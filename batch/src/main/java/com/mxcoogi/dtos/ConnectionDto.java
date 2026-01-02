package com.mxcoogi.dtos;

import lombok.Data;

@Data
public class ConnectionDto {

    private String sourceType;
    private String sourceUrl;
    private String sourceUsername;
    private String sourcePassword;

    private String targetType;
    private String targetUrl;
    private String targetUsername;
    private String targetPassword;
}
