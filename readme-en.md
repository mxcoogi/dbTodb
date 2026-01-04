# 📦 dbTodb – Database Migration Tool

`dbTodb` is a **Spring Boot + Spring Batch–based database migration tool**  
designed to **safely transfer table data between heterogeneous databases**.

The API server receives migration requests and triggers  
a Batch application to migrate data from **Source DB → Target DB**.

---

## ✨ Key Features

- 🔁 Table-level DB → DB data migration
- 🧩 Multi-database support (MySQL / PostgreSQL, extensible)
- ⚙️ Dynamic column mapping support
- 🚀 High-volume processing with Spring Batch
- 🐳 Single Docker image execution
- 📡 Batch Job triggered via API request

---

## 🏗 Project Structure (Multi-Module)

### 🔍 Core Design Principles

- Complete separation of **API** and **Batch**
- Batch runs as an **external process**
- DataSources are **created dynamically per Job execution**
- Writer uses a **simple AutoCommit-based insert strategy**
- No Spring Batch metadata tables (lightweight execution)

---

## 🐳 Docker Image Architecture

A single Docker image contains both **API and Batch JARs**.

- The API server starts on container launch
- When requested, it internally executes `batch.jar`

---

## 🚀 Running with Docker

### 1️⃣ Build & Run

```
./gradlew clean build
docker build -t db-migration-api .
docker run -p 8888:8888 db-migration-api
```

🛠 Tech Stack
- Java 21
- spring Boot 3.5.x
- ing Batch 5.x
- JDBC (NamedParameterJdbcTemplate)
- Gradle (Multi-module)
- Docker

## 🔮 Future Improvements
- Batch execution status tracking (JobExecution)
- Chunk-based bulk insert
- Retry / skip policies on failure
- docker-compose support (including databases)
- Batch container separation
- Parallel Step processing
- Progress monitoring and result reporting

---

## 🧾 Version History

> dbTodb is an incrementally evolving project.  
> Each version is managed based on **clearly defined functional milestones**.

---

### 🔹 v0.0.1 (Initial Release)

**Release Date:** 2026-01-04

#### ✨ Features
- API → Batch process execution architecture
- DB → DB table data migration
- MySQL / PostgreSQL support
- Spring Batch–based chunk processing
- Dynamic column mapping (`Map<String, Object>`)
- NamedParameterJdbcTemplate-based inserts
- Job-parameter-driven configuration
- Dynamic DataSource creation during Batch execution
- AutoCommit-based writer implementation
- Single Docker image (API + Batch included)

#### ⚠️ Limitations
- No Batch execution status tracking
- No bulk insert performance optimization
- No retry / rollback support
- No parallel Step processing
- No multi-table migration support
- No CLI support

---

### 🔜 v0.0.2 (Planned)

#### Planned Features
- Batch execution status query API
- JobExecution / StepExecution log management
- Error message returned on failure
- Enhanced basic validation
- CLI support