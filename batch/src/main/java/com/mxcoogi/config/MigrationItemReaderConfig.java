package com.mxcoogi.config;

import com.mxcoogi.components.DatasourceFactory;
import com.mxcoogi.enums.DatabaseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.ColumnMapRowMapper;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
@Configuration
public class MigrationItemReaderConfig {
    @Bean
    @StepScope
    public JdbcCursorItemReader<Map<String, Object>> migrationItemReader(
            DatasourceFactory datasourceFactory,
            @Value("#{jobParameters['sourceType']}") String sourceType,
            @Value("#{jobParameters['sourceUrl']}") String sourceUrl,
            @Value("#{jobParameters['sourceUsername']}") String sourceUsername,
            @Value("#{jobParameters['sourcePassword']}") String sourcePassword,
            @Value("#{jobParameters['sourceTable']}") String sourceTable

    ) {
        log.info("sourceType={}, sourceUrl={}, sourceUsername={}, sourcePassword={}"
                , sourceType, sourceUrl, sourceUsername, sourcePassword);

        DataSource sourceDs = datasourceFactory.create(
                DatabaseType.valueOf(sourceType),
                sourceUrl,
                sourceUsername,
                sourcePassword
        );
        try (var conn = sourceDs.getConnection()) {
            log.info("✅ Source DB connection success. url={}, db={}",
                    conn.getMetaData().getURL(),
                    conn.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            log.error("❌ Source DB connection failed", e);
            throw new IllegalStateException("Source DB connection failed", e);
        }

        String sql = "SELECT * FROM " + sourceTable;
        JdbcCursorItemReader<Map<String, Object>> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(sourceDs);
        reader.setSql(sql);
        reader.setRowMapper(new ColumnMapRowMapper());
        reader.setFetchSize(100); // 중요
        reader.setVerifyCursorPosition(false);

        return reader;
    }
}
