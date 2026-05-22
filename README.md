# 📈 StockQ — 주식 논리 트레이닝 플랫폼

> 실제 과거 뉴스와 공시로 배우는 게이미피케이션 주식 교육 서비스  
> **중고등학생을 위한 "주식판 듀오링고"**

---

## 🎯 프로젝트 소개

StockQ는 실제 발생한 과거 주식 관련 뉴스·공시를 기반으로 **4가지 유형의 퀴즈**를 풀고,
모르는 용어는 **나만의 단어장**에 저장하고, 틀린 문제는 **오답노트**에 자동 기록되어
나만의 학습 자산을 쌓아가는 금융 교육 플랫폼입니다.

```
단순히 "오른다/내린다"가 아니라
섹터 → 밸류체인 → 기업 영향까지 단계적으로 공부할 수 있습니다.
```

---

## ✨ 주요 기능

### 📝 퀴즈 시스템 — 4가지 유형, 랜덤 혼합 출제
매일 제공되는 문제 세트에 4가지 유형이 랜덤으로 섞여 나옵니다. 유형을 선택하는 방식이 아니며, 연쇄 문제도 4가지 유형 중 하나로 랜덤 등장합니다.

| 유형 | 설명 | 예시 |
|---|---|---|
| 호재/악재 판단 | 뉴스가 특정 섹터에 미치는 영향 판단 | 배터리 공장 가동률 하락 → 배터리 셀 기업에 악재? |
| 섹터/밸류체인 파악 | 뉴스의 수혜·피해 기업 유형 선택 | 관세 인상 → 국내 양극재 기업 수혜? |
| 용어 퀴즈 | 핵심 금융 용어의 뜻과 맥락 이해 | PER이 낮다는 것의 의미는? |
| 연쇄 문제 (뉴스 카드형) | 뉴스 1개 → 3~5문제 단계적 심화 | 섹터→밸류체인→기업 영향 순서로 출제 |

### 📖 나만의 단어장
- 퀴즈 내 금융 용어 클릭 → **뜻 표시 없이 단어장에만 저장** (힌트 방지)
- 저장된 단어는 단어장에서 뜻 + 예문과 함께 확인
- 카드 플립 복습 모드 (앞: 용어 / 뒤: 뜻 + 예문)

### 🔖 나만의 문제집
- 마음에 드는 문제 북마크 저장
- 저장한 문제만 모아서 복습 모드로 재풀기

### ❌ 오답노트
- 틀린 문제 자동 저장 (별도 설정 불필요)
- 오답만 모아서 재도전 모드 제공
- 오답 유형 분석: "섹터 파악 문제를 자주 틀립니다"

### 🎮 게이미피케이션
- 포인트 & 레벨 시스템 (개미 → 주린이 → 투자자 → 고수)
- 연속 출석 스트릭 / 주간 랭킹 보드

---

## 🛠 기술 스택

### Frontend
![Vue.js](https://img.shields.io/badge/Vue.js-3.x-4FC08D?logo=vue.js)
![Pinia](https://img.shields.io/badge/Pinia-2.x-yellow)
![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-3.x-06B6D4?logo=tailwindcss)
![ApexCharts](https://img.shields.io/badge/ApexCharts-3.x-FF6384)

### Backend
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?logo=springboot)
![MyBatis](https://img.shields.io/badge/MyBatis-3.x-red)
![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?logo=mysql)
![JWT](https://img.shields.io/badge/JWT-Auth-black)

### Infra
![AWS EC2](https://img.shields.io/badge/AWS-EC2-FF9900?logo=amazonaws)
![Docker](https://img.shields.io/badge/Docker-blue?logo=docker)

---

## 📁 프로젝트 구조

```
StockQ/
├── frontend/
│   └── src/
│       ├── components/
│       │   ├── quiz/          # 퀴즈 컴포넌트 (4가지 유형)
│       │   │   ├── QuizCard.vue
│       │   │   ├── QuizChain.vue      # 연쇄 문제 세트
│       │   │   └── TermPopup.vue      # 용어 팝업
│       │   ├── chart/         # 주가 차트
│       │   └── common/
│       ├── views/
│       │   ├── HomeView.vue
│       │   ├── QuizView.vue
│       │   ├── ResultView.vue
│       │   ├── RankingView.vue
│       │   └── mypage/
│       │       ├── MyPageView.vue
│       │       ├── VocabularyView.vue   # 나만의 단어장
│       │       ├── SavedQuizView.vue    # 나만의 문제집
│       │       └── WrongAnswerView.vue  # 오답노트
│       ├── stores/
│       │   ├── auth.js
│       │   ├── quiz.js
│       │   ├── vocabulary.js  # 단어장 상태
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

## 🚀 실행 방법

### 사전 요구사항
- Java 17+, Node.js 18+, MySQL 8.x, Docker (선택)

### Backend 실행
```bash
cd backend
cp src/main/resources/application.yml.example src/main/resources/application.yml
./gradlew bootRun
```

### Frontend 실행
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

## 🔑 환경변수 (application.yml)
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

## 📊 주요 API

Swagger UI: `http://localhost:8080/swagger-ui.html`

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
| GET | `/api/vocabulary/{term}` | 용어 뜻 조회 (팝업) |
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

## 👥 팀원

| 이름 | 역할 | 담당 |
|---|---|---|
| 개발자 A | 백엔드 리드 | Spring Boot, MyBatis, 외부 API, 문제·용어 큐레이션 |
| 개발자 B | 프론트엔드 리드 | Vue.js, 단어장/오답노트 UI, 차트 연동 |

---

## 📝 License
MIT License
