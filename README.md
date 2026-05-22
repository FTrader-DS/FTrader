# 📈 FTrader — 주식 논리 트레이닝 플랫폼

> 실제 과거 뉴스와 공시로 배우는 게이미피케이션 주식 교육 서비스  
> **중고등학생을 위한 "주식판 듀오링고"**

---

## 🎯 프로젝트 소개

FTrader는 실제 발생한 과거 주식 관련 뉴스·공시를 기반으로 **4가지 유형의 퀴즈**를 풀고,
모르는 용어는 **나만의 단어장**에 저장하고, 틀린 문제는 **오답노트**에 자동 기록되어
나만의 학습 자산을 쌓아가는 금융 교육 플랫폼입니다.

```
단순히 "오른다/내린다"가 아니라
섹터 → 밸류체인 → 기업 영향까지 단계적으로 공부할 수 있습니다.
```

---

## ✨ 주요 기능

### 📝 퀴즈 시스템 — 4가지 유형, 랜덤 혼합 출제

| 유형 | 설명 |
|---|---|
| 호재/악재 판단 | 뉴스가 특정 섹터에 미치는 영향 판단 |
| 섹터/밸류체인 파악 | 뉴스의 수혜·피해 기업 유형 선택 |
| 용어 퀴즈 | 핵심 금융 용어의 뜻과 맥락 이해 |
| 연쇄 문제 (뉴스 카드형) | 뉴스 1개 → 3~5문제 단계적 심화 |

### 📖 나만의 단어장
퀴즈 내 용어 클릭 → 뜻 표시 없이 저장 (힌트 방지) → 단어장에서 뜻 + 예문 확인

### 🔖 나만의 문제집 / ❌ 오답노트
북마크 저장 및 오답 자동 저장 → 복습 모드 / 재도전 모드

### 🎮 게이미피케이션
포인트 & 레벨 (개미 → 주린이 → 투자자 → 고수) · 스트릭 · 주간 랭킹

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

## 🚀 실행 방법

### 사전 요구사항
- Java 17+, Node.js 18+, Docker

### Docker Compose (권장)
```bash
# backend/.env 파일 생성 후 실행 (아래 환경변수 섹션 참고)
docker-compose up -d
```

### 개별 실행

**Backend**
```bash
cd backend
cp src/main/resources/application.yml.example src/main/resources/application.yml
# application.yml 값 채운 후
./mvnw spring-boot:run
```

**Frontend**
```bash
cd frontend
npm install
npm run dev
```

---

## 🔑 환경변수

`backend/.env` 파일을 생성하고 아래 값을 채워주세요. (`.env`는 `.gitignore`에 포함되어 있습니다)

```env
DB_USERNAME=ftrader
DB_PASSWORD=your_db_password

JWT_SECRET=your-256-bit-secret-key-here-must-be-long-enough

KAKAO_CLIENT_ID=your_kakao_rest_api_key
KAKAO_CLIENT_SECRET=your_kakao_client_secret
KAKAO_REDIRECT_URI=http://localhost:5173/auth/callback
```

---

## 📊 주요 API

Swagger UI: `http://localhost:8080/swagger-ui.html`

| Method | URL | 설명 |
|---|---|---|
| GET | `/api/quiz/today` | 오늘의 퀴즈 목록 |
| GET | `/api/quiz/{id}` | 문제 상세 조회 |
| POST | `/api/quiz/{id}/submit` | 답안 제출 |
| POST | `/api/quiz/{id}/bookmark` | 북마크 토글 |
| GET | `/api/vocabulary/today` | 오늘의 용어 |
| POST | `/api/user/vocabulary` | 단어장 저장 |
| GET | `/api/user/vocabulary` | 내 단어장 조회 |
| GET | `/api/user/me` | 내 정보 조회 |
| GET | `/api/user/wrong-answers` | 오답노트 조회 |
| GET | `/api/user/saved-quiz` | 나만의 문제집 조회 |
| GET | `/api/rank/weekly` | 주간 랭킹 |

---

## 👥 팀원

| 이름 | 역할 | 담당 |
|---|---|---|
| 개발자 A | 백엔드 리드 | Spring Boot, MyBatis, 외부 API, 문제·용어 큐레이션 |
| 개발자 B | 프론트엔드 리드 | Vue.js, 단어장/오답노트 UI, ApexCharts 연동 |

---

## 🌿 브랜치 전략

두 명이 기능을 동시에 개발하므로 `dev` 브랜치를 통합 검증 공간으로 사용합니다.
기능 브랜치들은 `dev`으로 먼저 합쳐서 함께 테스트한 뒤, `main`으로 올립니다.

```
main                    # 배포 브랜치 — Ruleset으로 직접 push 금지
└── dev                # 통합 브랜치 — 기능 합쳐서 같이 테스트하는 공간
    ├── feat/기능명      # 새 기능 개발
    ├── fix/버그명       # 버그 수정
    ├── refactor/내용   # 리팩터링 (기능 변화 없음)
    └── docs/내용       # 문서 수정
```

### 브랜치 네이밍 규칙

| 유형 | 형식 | 예시 |
|---|---|---|
| 기능 개발 | `feat/기능명` | `feat/vocabulary-today-api` |
| 버그 수정 | `fix/버그명` | `fix/login-port-conflict` |
| 리팩터링 | `refactor/내용` | `refactor/add-rank-user-service` |
| 문서 수정 | `docs/내용` | `docs/update-readme` |

### 작업 흐름

```
feat/기능명 ──PR──▶ dev ──테스트 완료 후 PR──▶ main
```

```bash
# 1. dev 최신화 후 작업 브랜치 생성
git checkout dev
git pull origin dev
git checkout -b feat/기능명

# 2. 작업 후 커밋
git add .
git commit -m "feat: 오늘의 용어 API 추가"

# 3. 원격 push
git push origin feat/기능명

# 4. GitHub에서 dev 으로 PR 생성 → 상대방 Approve → merge
# 5. 두 기능 모두 dev에 합쳐지면 → dev → main PR 생성
```
---

## 📝 커밋 메시지 규칙

```
타입: 한 줄 요약 (50자 이내)
```

### 타입 목록

| 타입 | 설명 |
|---|---|
| `feat` | 새 기능 추가 |
| `fix` | 버그 수정 |
| `refactor` | 코드 리팩터링 (기능 변화 없음) |
| `docs` | 문서 수정 (README 등) |
| `style` | 코드 포맷, 세미콜론 등 (기능 변화 없음) |
| `chore` | 빌드 설정, 패키지 등 기타 변경 |

### 예시

```bash
feat: 오늘의 용어 API 및 홈 화면 카드 추가
```

```bash
fix: Docker MySQL 포트 충돌 수정

- 로컬 MySQL과 충돌 방지를 위해 3306 → 3307로 변경
```

---

## 🔀 PR 규칙

### PR 생성 규칙

- **base 브랜치**: 항상 `dev` (main으로 직접 PR 금지)
- **제목 형식**: `[타입] 작업 내용 요약`
    - 예: `[feat] 오늘의 용어 API 추가`, `[fix] 로그인 오류 수정`
- **PR 크기**: 하나의 PR은 하나의 기능 또는 하나의 버그 수정

### 리뷰 규칙

- PR은 **상대방 확인(Approve) 후에만 merge**
- 리뷰 없이 본인이 직접 merge 금지
- 리뷰 코멘트는 `Resolve` 처리 후 merge

---

## 📝 License
MIT License
