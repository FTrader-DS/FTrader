package com.ftrader.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 오답 유형 분석 — "섹터 파악 유형을 자주 틀립니다" */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WrongAnswerTypeAnalysis {
    private Integer quizType;
    private String quizTypeName;   // 호재/악재, 섹터/밸류체인, 용어, 연쇄
    private Long wrongCount;
}
