package com.ftrader.quiz.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubmitResponse {
    private boolean correct;
    private Long correctChoiceId;
    private String explanation;
    private int earnedPoints;
    private int totalPoints;
    private int streak;       // 현재 연속 출석일
    private boolean newAttend; // 오늘 첫 제출 여부 (프론트에서 스트릭 알림 표시용)
}
