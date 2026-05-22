# FTrader ERD v1.0

## 테이블 관계 다이어그램

```
users ──────────────── user_level          (1:1)
  │
  ├── quiz_result ─── quiz ─── quiz_choice  (N:1, 1:N)
  │                    │
  │                    ├── quiz_set_item ── quiz_set (N:1)
  │                    │
  │                    └── stock_price_snapshot      (1:N)
  │
  ├── user_vocabulary ── vocabulary ── daily_term    (N:1, 1:1)
  │                          │
  │                       sector ── theory           (1:1)
  │                          │
  │                       quiz (sector_id FK)
  │
  └── user_saved_quiz ── quiz                        (N:1)
```

---

## 테이블별 설명

### users / user_level
| 관계 | 설명 |
|---|---|
| users : user_level = 1:1 | 회원 가입 시 user_level 레코드 함께 생성 |
| kakao_id UNIQUE | 카카오 소셜 로그인 식별자 |

### quiz / quiz_choice / quiz_set / quiz_set_item
| 관계 | 설명 |
|---|---|
| quiz : quiz_choice = 1:N | 문제 1개당 선택지 4개 |
| quiz_set : quiz_set_item : quiz = 1:N:N | 연쇄 문제 세트가 여러 quiz를 순서대로 묶음 |
| quiz.quiz_type | 1:호재/악재, 2:섹터/밸류체인, 3:용어, 4:연쇄 |

> **연쇄 문제(유형 4) 처리 방식**
> - quiz 테이블에 개별 문제로 존재 (quiz_type=4)
> - quiz_set_item으로 세트 묶음 + 순서 지정
> - 퀴즈 랜덤 출제 시 세트 단위로 뽑아 순서대로 진행

### quiz_result
| 컬럼 | 설명 |
|---|---|
| is_correct=FALSE | 오답노트 소스 — 별도 테이블 없이 이 컬럼으로 오답 조회 |
| (user_id, is_correct) 복합 인덱스 | 오답노트 목록 조회 성능 |

### vocabulary / user_vocabulary / daily_term
| 관계 | 설명 |
|---|---|
| vocabulary : user_vocabulary = 1:N | 관리자가 등록한 용어를 사용자가 저장 |
| vocabulary : daily_term = 1:1 | 하루에 용어 1개 고정 노출 (display_date UNIQUE) |
| user_vocabulary (user_id, vocab_id) UNIQUE | 중복 저장 방지 |

> **단어장 힌트 방지 설계**
> - 퀴즈 화면에서 용어 클릭 시 `POST /api/user/vocabulary` 호출 (vocab_id만 전달)
> - API 응답에 definition 포함하지 않음 → 마이페이지 단어장에서만 확인 가능

### stock_price_snapshot
- quiz_id FK로 문제와 1:N 연결 (한 문제에 여러 종목 스냅샷 가능)
- 결과 화면에서 ApexCharts로 뉴스 전후 주가 시각화

### theory
- sector_id UNIQUE → 섹터당 이론집 1개
- content는 마크다운 저장, 프론트에서 렌더링

---

## 오답노트 조회 쿼리 예시

```sql
-- 오답 목록 (최신순)
SELECT q.quiz_id, q.question, q.quiz_type, s.name AS sector, qr.solved_at
FROM quiz_result qr
JOIN quiz q ON qr.quiz_id = q.quiz_id
JOIN sector s ON q.sector_id = s.sector_id
WHERE qr.user_id = #{userId}
  AND qr.is_correct = FALSE
ORDER BY qr.solved_at DESC;

-- 오답 유형 분석
SELECT q.quiz_type, COUNT(*) AS wrong_count
FROM quiz_result qr
JOIN quiz q ON qr.quiz_id = q.quiz_id
WHERE qr.user_id = #{userId}
  AND qr.is_correct = FALSE
GROUP BY q.quiz_type
ORDER BY wrong_count DESC;
```

## 오늘의 랜덤 퀴즈 세트 출제 쿼리 예시

```sql
-- 오늘 아직 안 푼 문제 중 랜덤 N개 (단일 문제)
SELECT q.quiz_id
FROM quiz q
WHERE q.quiz_type IN (1, 2, 3)
  AND q.is_active = TRUE
  AND q.quiz_id NOT IN (
      SELECT quiz_id FROM quiz_result WHERE user_id = #{userId}
  )
ORDER BY RAND()
LIMIT #{count};
```
