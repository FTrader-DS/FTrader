-- FTrader DB Schema v1.0
-- MySQL 8.x
-- charset: utf8mb4

CREATE DATABASE IF NOT EXISTS ftrader DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ftrader;

-- ============================================================
-- 1. 사용자
-- ============================================================

CREATE TABLE users (
    user_id     BIGINT          NOT NULL AUTO_INCREMENT,
    kakao_id    VARCHAR(64)     NOT NULL,
    nickname    VARCHAR(50)     NOT NULL,
    profile_img VARCHAR(500),
    created_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    UNIQUE KEY uq_kakao_id (kakao_id)
) ENGINE=InnoDB;

CREATE TABLE user_level (
    user_id         BIGINT      NOT NULL,
    points          INT         NOT NULL DEFAULT 0,
    level           TINYINT     NOT NULL DEFAULT 1 COMMENT '1:개미 2:주린이 3:투자자 4:고수',
    streak          INT         NOT NULL DEFAULT 0 COMMENT '연속 출석일',
    last_attend_dt  DATE,
    updated_at      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    CONSTRAINT fk_user_level_user FOREIGN KEY (user_id) REFERENCES users (user_id)
) ENGINE=InnoDB;

-- ============================================================
-- 2. 섹터
-- ============================================================

CREATE TABLE sector (
    sector_id   TINYINT         NOT NULL AUTO_INCREMENT,
    name        VARCHAR(50)     NOT NULL COMMENT '반도체, AI·데이터센터 등',
    group_name  VARCHAR(30)     NOT NULL COMMENT '테크, 에너지·소재 등',
    PRIMARY KEY (sector_id)
) ENGINE=InnoDB;

-- ============================================================
-- 3. 용어 (단어장 소스)
-- ============================================================

CREATE TABLE vocabulary (
    vocab_id    BIGINT          NOT NULL AUTO_INCREMENT,
    term        VARCHAR(100)    NOT NULL,
    definition  TEXT            NOT NULL,
    example     TEXT,
    category    VARCHAR(50)     COMMENT '기초 지표, 재무제표, 시장 구조, 섹터 용어, 투자 심리',
    sector_id   TINYINT         COMMENT '섹터 전용 용어일 경우',
    created_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (vocab_id),
    CONSTRAINT fk_vocab_sector FOREIGN KEY (sector_id) REFERENCES sector (sector_id)
) ENGINE=InnoDB;

CREATE TABLE daily_term (
    daily_term_id   INT         NOT NULL AUTO_INCREMENT,
    vocab_id        BIGINT      NOT NULL,
    display_date    DATE        NOT NULL,
    PRIMARY KEY (daily_term_id),
    UNIQUE KEY uq_display_date (display_date),
    CONSTRAINT fk_daily_term_vocab FOREIGN KEY (vocab_id) REFERENCES vocabulary (vocab_id)
) ENGINE=InnoDB;

-- ============================================================
-- 4. 퀴즈
-- ============================================================

CREATE TABLE quiz (
    quiz_id         BIGINT      NOT NULL AUTO_INCREMENT,
    sector_id       TINYINT     NOT NULL,
    quiz_type       TINYINT     NOT NULL COMMENT '1:호재/악재 2:섹터/밸류체인 3:용어 4:연쇄',
    difficulty      TINYINT     NOT NULL DEFAULT 1 COMMENT '1:초급 2:중급 3:고급',
    news_content    TEXT        COMMENT '뉴스·공시 원문 (유형 1·2·4)',
    question        TEXT        NOT NULL,
    explanation     TEXT        NOT NULL COMMENT '해설',
    is_active       BOOLEAN     NOT NULL DEFAULT TRUE,
    created_at      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (quiz_id),
    CONSTRAINT fk_quiz_sector FOREIGN KEY (sector_id) REFERENCES sector (sector_id)
) ENGINE=InnoDB;

CREATE TABLE quiz_choice (
    choice_id   BIGINT      NOT NULL AUTO_INCREMENT,
    quiz_id     BIGINT      NOT NULL,
    choice_no   TINYINT     NOT NULL COMMENT '1~4',
    content     VARCHAR(500) NOT NULL,
    is_correct  BOOLEAN     NOT NULL DEFAULT FALSE,
    PRIMARY KEY (choice_id),
    KEY idx_quiz_choice_quiz (quiz_id),
    CONSTRAINT fk_quiz_choice_quiz FOREIGN KEY (quiz_id) REFERENCES quiz (quiz_id)
) ENGINE=InnoDB;

-- 연쇄 문제 세트 (유형 4)
-- 뉴스 1개에 여러 문제가 묶임
CREATE TABLE quiz_set (
    set_id          BIGINT      NOT NULL AUTO_INCREMENT,
    sector_id       TINYINT     NOT NULL,
    title           VARCHAR(200),
    news_content    TEXT        NOT NULL COMMENT '세트 공통 뉴스 카드',
    is_active       BOOLEAN     NOT NULL DEFAULT TRUE,
    created_at      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (set_id),
    CONSTRAINT fk_quiz_set_sector FOREIGN KEY (sector_id) REFERENCES sector (sector_id)
) ENGINE=InnoDB;

CREATE TABLE quiz_set_item (
    item_id     BIGINT      NOT NULL AUTO_INCREMENT,
    set_id      BIGINT      NOT NULL,
    quiz_id     BIGINT      NOT NULL,
    item_order  TINYINT     NOT NULL COMMENT '세트 내 문제 순서',
    PRIMARY KEY (item_id),
    UNIQUE KEY uq_set_order (set_id, item_order),
    CONSTRAINT fk_set_item_set  FOREIGN KEY (set_id)  REFERENCES quiz_set (set_id),
    CONSTRAINT fk_set_item_quiz FOREIGN KEY (quiz_id) REFERENCES quiz (quiz_id)
) ENGINE=InnoDB;

-- ============================================================
-- 5. 풀이 결과 (오답노트 포함)
-- ============================================================

CREATE TABLE quiz_result (
    result_id           BIGINT      NOT NULL AUTO_INCREMENT,
    user_id             BIGINT      NOT NULL,
    quiz_id             BIGINT      NOT NULL,
    selected_choice_id  BIGINT      NOT NULL,
    is_correct          BOOLEAN     NOT NULL,
    solved_at           DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (result_id),
    UNIQUE KEY uq_user_quiz_result (user_id, quiz_id),
    KEY idx_result_user     (user_id),
    KEY idx_result_wrong    (user_id, is_correct),
    CONSTRAINT fk_result_user   FOREIGN KEY (user_id)           REFERENCES users (user_id),
    CONSTRAINT fk_result_quiz   FOREIGN KEY (quiz_id)           REFERENCES quiz (quiz_id),
    CONSTRAINT fk_result_choice FOREIGN KEY (selected_choice_id) REFERENCES quiz_choice (choice_id)
) ENGINE=InnoDB;

-- ============================================================
-- 6. 사용자 학습 자산
-- ============================================================

CREATE TABLE user_vocabulary (
    user_vocab_id   BIGINT      NOT NULL AUTO_INCREMENT,
    user_id         BIGINT      NOT NULL,
    vocab_id        BIGINT      NOT NULL,
    saved_at        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_vocab_id),
    UNIQUE KEY uq_user_vocab (user_id, vocab_id),
    CONSTRAINT fk_user_vocab_user  FOREIGN KEY (user_id)  REFERENCES users (user_id),
    CONSTRAINT fk_user_vocab_vocab FOREIGN KEY (vocab_id) REFERENCES vocabulary (vocab_id)
) ENGINE=InnoDB;

CREATE TABLE user_saved_quiz (
    saved_quiz_id   BIGINT      NOT NULL AUTO_INCREMENT,
    user_id         BIGINT      NOT NULL,
    quiz_id         BIGINT      NOT NULL,
    memo            VARCHAR(300),
    saved_at        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (saved_quiz_id),
    UNIQUE KEY uq_user_saved_quiz (user_id, quiz_id),
    CONSTRAINT fk_saved_quiz_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_saved_quiz_quiz FOREIGN KEY (quiz_id) REFERENCES quiz (quiz_id)
) ENGINE=InnoDB;

-- ============================================================
-- 7. 주가 스냅샷 (해설 화면 차트용)
-- ============================================================

CREATE TABLE stock_price_snapshot (
    snapshot_id         BIGINT          NOT NULL AUTO_INCREMENT,
    quiz_id             BIGINT          NOT NULL,
    stock_code          VARCHAR(20)     NOT NULL COMMENT '종목 코드',
    stock_name          VARCHAR(50)     NOT NULL,
    event_date          DATE            NOT NULL COMMENT '뉴스 발생일',
    price_before        INT             NOT NULL COMMENT '뉴스 발생 전 종가',
    price_after         INT             NOT NULL COMMENT '뉴스 발생 후 종가',
    change_rate         DECIMAL(5, 2)   NOT NULL COMMENT '등락률 (%)',
    PRIMARY KEY (snapshot_id),
    KEY idx_snapshot_quiz (quiz_id),
    CONSTRAINT fk_snapshot_quiz FOREIGN KEY (quiz_id) REFERENCES quiz (quiz_id)
) ENGINE=InnoDB;

-- ============================================================
-- 8. 이론집 (섹터별 학습 자료)
-- ============================================================

CREATE TABLE theory (
    theory_id   INT         NOT NULL AUTO_INCREMENT,
    sector_id   TINYINT     NOT NULL,
    title       VARCHAR(200) NOT NULL,
    content     LONGTEXT    NOT NULL COMMENT '마크다운 본문',
    version     TINYINT     NOT NULL DEFAULT 1,
    updated_at  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (theory_id),
    UNIQUE KEY uq_theory_sector (sector_id),
    CONSTRAINT fk_theory_sector FOREIGN KEY (sector_id) REFERENCES sector (sector_id)
) ENGINE=InnoDB;

-- ============================================================
-- 기초 데이터 — 섹터 17개
-- ============================================================

INSERT INTO sector (name, group_name) VALUES
    ('반도체',              '테크'),
    ('AI·데이터센터',       '테크'),
    ('인터넷·플랫폼',       '테크'),
    ('이차전지',            '에너지·소재'),
    ('원전',                '에너지·소재'),
    ('화학',                '에너지·소재'),
    ('정유',                '에너지·소재'),
    ('조선',                '산업·제조'),
    ('방산',                '산업·제조'),
    ('자동차',              '산업·제조'),
    ('로봇',                '산업·제조'),
    ('우주항공',            '산업·제조'),
    ('바이오·제약',         '소비·금융'),
    ('화장품',              '소비·금융'),
    ('금융',                '소비·금융'),
    ('건설·부동산·인프라',  '소비·금융'),
    ('코인·블록체인',       '기타');
