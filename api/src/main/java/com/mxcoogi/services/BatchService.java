package com.mxcoogi.services;

import com.mxcoogi.components.JsonUtilComponent;
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
    private final JsonUtilComponent jsonUtil;


    public void startBatch(BatchInfo request) {
        List<MappingDto> mappingDto = request.getMappingDto();
        ConnectionDto connectionDto = request.getConnectionDto();
        String jsonConnection = jsonUtil.fromConnection(connectionDto);
        String jsonMapping = jsonUtil.fromMappings(mappingDto);
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "-jar",
                    "/Users/mxcoogi/dev/dbTodb/batch/build/libs/batch-1.0-SNAPSHOT.jar",
                    "--spring.batch.job.name=" + "simpleJob"
            );
            log.info(pb.toString());

            pb.inheritIO();
            pb.start();
        }catch (Exception e){

        }
    }
}
