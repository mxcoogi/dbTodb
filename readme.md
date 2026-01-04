# 📦 dbTodb – Database Migration Tool

`dbTodb`는 **서로 다른 데이터베이스 간 테이블 데이터를 안전하게 이관**하기 위한  
**Spring Boot + Spring Batch 기반 데이터 마이그레이션 도구**입니다.

API 서버를 통해 마이그레이션 요청을 받고,  
Batch 애플리케이션을 실행하여 **Source DB → Target DB**로 데이터를 이전합니다.

---
[english docs](readme-en.md)

## ✨ 주요 기능

- 🔁 DB → DB 테이블 데이터 마이그레이션
- 🧩 MySQL / PostgreSQL 등 다중 DB 지원 (확장 가능)
- ⚙️ 컬럼 매핑 지원 (동적 매핑)
- 🚀 Spring Batch 기반 대용량 처리
- 🐳 Docker 기반 단일 이미지 실행
- 📡 API 요청으로 Batch Job 트리거

---

## 🏗 프로젝트 구조 (멀티 모듈)

### 🔍 핵심 설계 포인트

- API와 Batch를 **완전히 분리**
- Batch는 **외부 프로세스**로 실행
- DataSource는 **Job 실행 시 동적 생성**
- Writer는 **AutoCommit 기반 단순 Insert 전략**
- Batch 메타데이터 테이블 미사용 (경량 실행)

---

## 🐳 Docker 이미지 구성

하나의 Docker 이미지 안에 **API + Batch JAR**를 모두 포함합니다.

API 서버가 실행되고,  
요청 시 내부에서 `batch.jar`를 실행합니다.

---

## 🚀 Docker로 실행하기

### 1️⃣ 빌드

```
./gradlew clean build
docker build -t db-migration-api .
docker run -p 8888:8888 db-migration-api
http://localhost:8888
```


## 🛠 기술 스택
- Java 21
- spring Boot 3.5.x
- ing Batch 5.x
- JDBC (NamedParameterJdbcTemplate)
- Gradle (Multi-module)
- Docker


## 🔮 향후 개선 계획
- 	Batch 실행 상태 추적 (JobExecution)
- 	Chunk 기반 Bulk Insert
-	실패 재시도 / Skip 정책
-	docker-compose (DB 포함)
-	Batch 컨테이너 분리
-	병렬 Step 처리
-   진행상태 모니터링 및 결과

---

## 🧾 Version History

> dbTodb는 기능 단위로 점진적으로 확장되는 프로젝트입니다.  
> 각 버전은 **명확한 기능 단위 기준**으로 관리됩니다.

---

### 🔹 v0.0.1 (Initial Release)

**Release Date:** 2026-01-04

#### ✨ Features
- API → Batch 프로세스 실행 구조 구축
- DB → DB 테이블 데이터 마이그레이션
- MySQL / PostgreSQL 지원
- Spring Batch 기반 Chunk 처리
- 컬럼 동적 매핑 (`Map<String, Object>`)
- NamedParameterJdbcTemplate 기반 Insert
- Job Parameter 기반 설정 전달
- Batch 실행 시 DataSource 동적 생성
- AutoCommit 기반 Writer 처리
- 단일 Docker 이미지 (API + Batch 포함)

#### ⚠️ Limitations
- Batch 실행 상태 추적 불가
- 대량 Insert 성능 최적화 미적용
- 실패 재시도 / 롤백 미지원
- 병렬 Step 미지원
- 다중 테이블 마이그레이션 미적용
- CLI 미적용
---

### 🔜 v0.0.2 (Planned)

#### 예정 기능
- Batch 실행 상태 조회 API
- JobExecution / StepExecution 로그 관리
- 실패 시 에러 메시지 반환
- 기본 Validation 강화
- CLI 지원
