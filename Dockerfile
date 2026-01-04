FROM eclipse-temurin:21-jre

# 작업 디렉토리
WORKDIR /app

# api / batch jar 복사
COPY api/build/libs/api-*.jar /app/api.jar
COPY batch/build/libs/batch-*.jar /app/batch.jar

# API 포트
EXPOSE 8888

# 기본 실행은 API
ENTRYPOINT ["java", "-jar", "/app/api.jar"]