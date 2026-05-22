package com.ftrader.quiz.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuizSetResponse {
    private Long setId;
    private String sectorName;
    private String title;
    private String newsContent;
    private List<VocabTermRef> vocabTerms;
    private List<QuizDetailResponse> quizzes;
}
