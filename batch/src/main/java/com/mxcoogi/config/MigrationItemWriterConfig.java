package com.mxcoogi.config;

import com.mxcoogi.components.DatasourceFactory;
import com.mxcoogi.enums.DatabaseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

@Configuration
@Slf4j
public class MigrationItemWriterConfig {

    @Bean
    @StepScope
    public ItemWriter<Map<String, Object>> migrationItemWriter(
            DatasourceFactory datasourceFactory,
            @Value("#{jobParameters['targetType']}") String targetType,
            @Value("#{jobParameters['targetUrl']}") String targetUrl,
            @Value("#{jobParameters['targetUsername']}") String targetUsername,
            @Value("#{jobParameters['targetPassword']}") String targetPassword,
            @Value("#{jobParameters['targetTable']}") String targetTable
    ) {
        log.info("sourceType={}, sourceUrl={}, sourceUsername={}, sourcePassword={}"
                , targetType, targetUrl, targetUsername, targetPassword);

        DataSource targetDs = datasourceFactory.create(
                DatabaseType.valueOf(targetType),
                targetUrl,
                targetUsername,
                targetPassword
        );

        NamedParameterJdbcTemplate template =
                new NamedParameterJdbcTemplate(targetDs);
        log.info("go insert : {}, {}", targetType, targetTable );

        return items -> {
            for (Map<String, Object> item : items) {

                // 1️⃣ 컬럼 목록 생성
                String columns = String.join(", ", item.keySet());

                // 2️⃣ :col 형태의 named parameter 생성
                String values = item.keySet().stream()
                        .map(k -> ":" + k)
                        .reduce((a, b) -> a + ", " + b)
                        .orElseThrow();

                // 3️⃣ SQL 완성
                String sql = "INSERT INTO " + targetTable +
                        " (" + columns + ") VALUES (" + values + ")";

                log.info("🧾 INSERT SQL = {}", sql);
                log.info("📦 PARAMS = {}", item);

                // 4️⃣ 실행
                int update = template.update(sql, item);
                log.info("insert {} ", update);
            }

        };
    }
}
