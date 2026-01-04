package com.mxcoogi.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxcoogi.dtos.BatchInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchService {


    private final ObjectMapper objectMapper;

    public void startBatch(BatchInfo request) {

        String mappingJson;
        try {
            mappingJson = objectMapper.writeValueAsString(request.getMappingDto());
            // Base64로 인코딩
            String encodedMapping = Base64.getEncoder().encodeToString(mappingJson.getBytes());
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-jar",
                    "/app/batch.jar",
                    "--spring.batch.job.name=" + "migrationJob",
                    "sourceType=" + request.getConnectionDto().getSourceType(),
                    "sourceUrl=" + request.getConnectionDto().getSourceUrl(),
                    "sourceUsername=" + request.getConnectionDto().getSourceUsername(),
                    "sourcePassword=" + request.getConnectionDto().getSourcePassword(),
                    "sourceTable=" + request.getSourceTable(),
                    "targetType=" + request.getConnectionDto().getTargetType(),
                    "targetUrl=" + request.getConnectionDto().getTargetUrl(),
                    "targetUsername=" + request.getConnectionDto().getTargetUsername(),
                    "targetPassword=" + request.getConnectionDto().getTargetPassword(),
                    "targetTable=" + request.getTargetTable(),
                    "mapping=" + encodedMapping
            );
            pb.inheritIO();
            pb.start();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
