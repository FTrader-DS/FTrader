package com.ftrader.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuizChoice {
    private Long choiceId;
    private Long quizId;
    private Integer choiceNo;
    private String content;
    private Boolean isCorrect;
}
