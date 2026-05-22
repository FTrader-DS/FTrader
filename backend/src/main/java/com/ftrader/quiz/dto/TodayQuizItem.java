package com.ftrader.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodayQuizItem {
    private String itemType;       // "SINGLE" | "SET"
    private Long quizId;           // SINGLE 전용
    private Long setId;            // SET 전용
    private Integer quizType;      // 1:호재/악재 2:섹터 3:용어 4:연쇄
    private String sectorName;
    private Integer difficulty;
    private Integer questionCount; // SET 전용 — 세트 내 문제 수
}
