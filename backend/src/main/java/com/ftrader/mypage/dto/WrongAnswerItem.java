package com.ftrader.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WrongAnswerItem {
    private Long resultId;
    private Long quizId;
    private Integer quizType;
    private Integer difficulty;
    private String sectorName;
    private String question;
    private String selectedContent;  // 사용자가 선택한 답
    private String correctContent;   // 정답 텍스트
    private String explanation;
    private LocalDateTime solvedAt;
}
