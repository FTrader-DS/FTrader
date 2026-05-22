package com.ftrader.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SavedQuizItem {
    private Long savedQuizId;
    private Long quizId;
    private Integer quizType;
    private Integer difficulty;
    private String sectorName;
    private String question;
    private String memo;
    private LocalDateTime savedAt;
}
