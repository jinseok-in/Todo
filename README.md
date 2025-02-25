# 할 일 (ToDo) 관리 앱

## 1. 개요
**React.JS**(^19.0.0)와 **Spring-Boot**(v3.4.2)를 이용하여 구현한 **할 일 관리 (ToDo)** 앱입니다.

할 일을 등록, 수정 및 삭제할 수 있으며, 마감 기한을 설정할 수 있습니다.  
현재 날짜를 기준으로 마감 기한이 지난 경우 기한 만료로 표시되며, 아직 기한이 남아있는 경우 완료 여부를 직접 관리할 수 있습니다.  
모든 데이터는 마감 기한을 기준으로 오름차순 정렬되어 보여집니다.

### 주요 기능
1. **할 일 CRUD (생성, 조회, 수정, 삭제)**
   - 조회 시 마감 기한을 기준으로 오름차순 정렬
2. **마감 기한 설정 및 확인**
   - 기간 만료, 미완료, 완료 상태 표시
3. **완료 여부 체크**

---

## 2. 개발 환경

### Frontend
- Node.JS 21.7
- React.JS 19.0

### Backend
- JDK 21
- Gradle
- MySQL 8.0

---

## 3. 소스 빌드 및 실행 방법

### Frontend 실행 방법
```bash
# 디렉토리로 이동  
cd frontend
# 필요 패키지 설치
npm install
# 어플리케이션 실행
npm start
```
실행 후 http://localhost:3000 접속  

### Backend 실행 방법
```bash
# 디렉토리로 이동
cd backend
# 어플리케이션 빌드
./gradlew build
# 어플리케이션 실행
./gradlew bootRun
```
실행 후 http://localhost:8080 접속  

### MySQL 세팅
1. `resources/application.properties` 파일에서 `username`, `password`를 실행 환경 계정 정보에 맞게 변경  
2. 데이터베이스 생성:
```sql
# 데이터베이스 생성
CREATE DATABASE todo;
```

### 기초 데이터
- Spring Boot 실행 시 `data.sql`이 자동으로 실행되어 데이터가 삽입됩니다.

### 데이터베이스 스키마
```sql
+-------------+--------------+------+-----+---------+----------------+
| Field       | Type         | Null | Key | Default | Extra          |
+-------------+--------------+------+-----+---------+----------------+
| is_checked  | bit(1)       | YES  |     | NULL    |                |
| create_time | datetime(6)  | NO   |     | NULL    |                |
| deadline    | datetime(6)  | NO   |     | NULL    |                |
| todo_id     | bigint       | NO   | PRI | NULL    | auto_increment |
| content     | varchar(255) | NO   |     | NULL    |                |
| description | varchar(255) | YES  |     | NULL    |                |
+-------------+--------------+------+-----+---------+----------------+
```

---

## 4. 라이브러리 사용 설명 및 사용 설명

### Frontend
- `axios` : RESTful API와 비동기 데이터 통신 및 요청/응답 데이터 JSON 변환을 위해 사용  

### Backend
- `spring-boot-starter-web` : RESTful API 개발을 위해 사용  
- `spring-boot-starter-test` : JUnit 기반의 테스트를 위해 사용  
- `org.projectlombok:lombok` : Getter, Setter를 자동화하여 코드량을 줄이기 위해 사용  

### 데이터베이스 관련
- `spring-boot-starter-data-jpa` : 데이터베이스 연동을 위해 사용  

---

## 5. API 명세
- Swagger 사용하여 문서화 했습니다.
- API 문서: http://localhost:8080/swagger-ui/index.html

