# FTrader — 주식 논리 트레이닝 플랫폼

## 프로젝트 개요

**FTrader**는 실제 과거 뉴스·공시 기반의 게이미피케이션 주식 금융 교육 서비스입니다.
중고등학생(14~19세)을 주 타겟으로 한 "주식판 듀오링고" 컨셉의 MVP입니다.

- 개발 기간: 4주 (1개월)
- 개발 인원: 2명 (백엔드 리드 A, 프론트엔드 리드 B)

---

## 기술 스택

### Frontend
- **Vue.js 3.x** + Vue Router 4.x
- **Pinia 2.x** — 상태 관리
- **Tailwind CSS 3.x** — 스타일링
- **ApexCharts 3.x** — 주가 차트 시각화
- **Axios 1.x** — HTTP 통신

### Backend
- **Spring Boot 3.x** + Spring Security 6.x
- **MyBatis 3.x** — SQL Mapper (JPA 아님)
- **JWT** — 토큰 기반 인증
- **Kakao OAuth2** — 소셜 로그인
- **Java 17**

### Database & 외부 API
- **MySQL 8.x** — 관계형 DB
- **DART Open API** — 공시 데이터 수집
- **KIS API** — 주가 데이터
- **Claude API** — 문제 해설 생성 보조

### Infra
- **AWS EC2 + RDS**
- **Docker / Docker Compose**
- **Swagger** — API 명세 (`/swagger-ui.html`)

---

## 프로젝트 구조

```
FTrader/
├── frontend/
│   └── src/
│       ├── components/
│       │   ├── quiz/
│       │   │   ├── QuizCard.vue          # 기본형 퀴즈 (유형 1·2)
│       │   │   ├── QuizChain.vue         # 연쇄 문제 세트 (유형 4)
│       │   │   └── TermSave.vue          # 용어 저장 컴포넌트 (힌트 방지)
│       │   ├── chart/
│       │   └── common/
│       ├── views/
│       │   ├── HomeView.vue
│       │   ├── QuizView.vue
│       │   ├── ResultView.vue
│       │   ├── RankingView.vue
│       │   └── mypage/
│       │       ├── MyPageView.vue
│       │       ├── VocabularyView.vue    # 나만의 단어장
│       │       ├── SavedQuizView.vue     # 나만의 문제집
│       │       └── WrongAnswerView.vue  # 오답노트
│       ├── stores/
│       │   ├── auth.js
│       │   ├── quiz.js
│       │   ├── vocabulary.js
│       │   └── user.js
│       └── api/
│
└── backend/
    └── src/main/java/com/stockq/
        ├── auth/
        ├── quiz/
        │   ├── controller/
        │   ├── service/
        │   ├── mapper/
        │   └── dto/
        ├── vocabulary/        # 용어/단어장 도메인
        ├── mypage/            # 단어장·문제집·오답노트
        ├── user/
        ├── rank/
        └── external/
            ├── dart/
            └── kis/
```

---

## 핵심 기능

### 퀴즈 시스템 — 4가지 유형 랜덤 혼합 출제

| 유형 | 설명 |
|---|---|
| 호재/악재 판단 | 뉴스가 특정 섹터에 미치는 영향 판단 |
| 섹터/밸류체인 파악 | 뉴스 수혜·피해 기업 유형 선택 |
| 용어 퀴즈 | 핵심 금융 용어 뜻과 맥락 이해 |
| 연쇄 문제 (뉴스 카드형) | 뉴스 1개 → 섹터→밸류체인→기업 순 3~5문제 |

- 유형 선택 방식이 **아님** — 4가지가 랜덤 혼합 출제
- 연쇄 문제도 동일 랜덤 풀에 포함

### 단어장 (용어 저장)
- 퀴즈 중 금융 용어 클릭 → **뜻은 즉시 표시하지 않고 저장만** (힌트 방지)
- 마이페이지 > 단어장에서 뜻 + 예문 확인 / 카드 플립 복습

### 나만의 문제집
- 북마크 아이콘으로 문제 저장 → 복습 모드 재풀기

### 오답노트
- 틀린 문제 **자동 저장** (별도 설정 불필요)
- 오답 유형 분석: "섹터 파악 문제를 자주 틀립니다"

### 게이미피케이션
- 포인트 & 레벨 (개미 → 주린이 → 투자자 → 고수)
- 연속 출석 스트릭 / 주간 랭킹

---

## DB 주요 테이블

| 테이블명 | 설명 |
|---|---|
| users | 사용자 정보 (카카오 연동) |
| quiz | 문제 기본 정보 (뉴스, 유형, 난이도, 카테고리) |
| quiz_set | 연쇄 문제 세트 |
| quiz_set_item | 세트 내 문제 순서 매핑 |
| quiz_choice | 선택지 |
| quiz_result | 사용자별 풀이 결과 (오답 포함) |
| vocabulary | 용어 정의 (관리자 등록) |
| user_vocabulary | 사용자 저장 단어장 |
| user_saved_quiz | 사용자 북마크 문제집 |
| user_level | 포인트, 레벨, 스트릭 |
| stock_price_snapshot | 문제 출제 시점 주가 스냅샷 |

---

## 주요 API 엔드포인트

### 퀴즈
| Method | URL | 설명 |
|---|---|---|
| GET | `/api/quiz/today` | 오늘의 퀴즈 목록 |
| GET | `/api/quiz/{id}` | 문제 상세 조회 |
| GET | `/api/quiz/set/{setId}` | 연쇄 문제 세트 조회 |
| POST | `/api/quiz/{id}/submit` | 답안 제출 |
| POST | `/api/quiz/{id}/bookmark` | 문제 북마크 저장 |

### 단어장
| Method | URL | 설명 |
|---|---|---|
| GET | `/api/vocabulary/{term}` | 용어 뜻 조회 |
| POST | `/api/user/vocabulary` | 단어장에 저장 |
| GET | `/api/user/vocabulary` | 내 단어장 조회 |
| DELETE | `/api/user/vocabulary/{id}` | 단어장에서 삭제 |

### 마이페이지
| Method | URL | 설명 |
|---|---|---|
| GET | `/api/user/me` | 내 정보 / 레벨 조회 |
| GET | `/api/user/saved-quiz` | 나만의 문제집 조회 |
| GET | `/api/user/wrong-answers` | 오답노트 조회 |
| GET | `/api/rank/weekly` | 주간 랭킹 |

---

## 환경변수 (application.yml)

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/stockq
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

kakao:
  client-id: ${KAKAO_CLIENT_ID}
jwt:
  secret: ${JWT_SECRET}
claude:
  api-key: ${CLAUDE_API_KEY}
kis:
  api-key: ${KIS_API_KEY}
dart:
  api-key: ${DART_API_KEY}
```

---

## 실행 방법

### 사전 요구사항
- Java 17+, Node.js 18+, MySQL 8.x, Docker (선택)

### Backend
```bash
cd backend
cp src/main/resources/application.yml.example src/main/resources/application.yml
mvn spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
cp .env.example .env
npm run dev
```

### Docker Compose
```bash
docker-compose up -d
```

---

## 개발 우선순위 (MVP 기준)

### Must Have
- 카카오 로그인 (JWT)
- 퀴즈 기본형 (유형 1·2) + 답안 제출
- 단어장 API & 화면 (힌트 방지 로직 포함)
- 나만의 문제집 API & 화면
- 오답노트 API & 화면 (자동 저장)
- 결과/해설 화면

### Should Have
- 연쇄 문제 (유형 4)
- 레벨/랭킹 시스템
- 섹터별 이론집

### Nice to Have (2차)
- KIS/DART 실시간 API 자동 연동
- 오답 유형 분석 AI 코멘트
- 포인트 모의 주식 투자 기능

---

## 콘텐츠 목표 (MVP)
- 문제 30개 (4가지 유형 혼합)
- 연쇄 문제 세트 5개 (세트당 3~4문제)
- 금융 용어 50개 (뜻 + 예문)
- 지원 섹터: 17개 (반도체, AI·데이터센터, 이차전지 등)

---

## 협업 도구
- **GitHub** — 버전 관리, 브랜치 전략
- **Notion** — 문제·용어 큐레이션 DB
- **Figma** — UI/UX 설계
- **Swagger** — API 명세 (`http://localhost:8080/swagger-ui.html`)
