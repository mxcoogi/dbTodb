package com.mxcoogi.services;

import com.mxcoogi.dtos.BatchInfo;
import com.mxcoogi.dtos.ConnectionDto;
import com.mxcoogi.dtos.MappingDto;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchService {


    public void startBatch(BatchInfo request) {
        List<MappingDto> mappingDto = request.getMappingDto();
        ConnectionDto connectionDto = request.getConnectionDto();
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "-jar",
                    "/path/to/batch.jar"
            );

            pb.inheritIO();
            pb.start();
        }catch (Exception e){

        }
    }
}
