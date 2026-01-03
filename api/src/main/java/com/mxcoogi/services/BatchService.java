package com.mxcoogi.services;


import com.mxcoogi.dtos.BatchInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchService {


    public void startBatch(BatchInfo request) {

        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "-jar",
                    "/Users/mxcoogi/dev/dbTodb/batch/build/libs/batch-1.0-SNAPSHOT.jar",
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
                    "targetTable=" + request.getTargetTable()
                    );

            pb.inheritIO();
            pb.start();
        }catch (Exception e){

        }
    }
}
