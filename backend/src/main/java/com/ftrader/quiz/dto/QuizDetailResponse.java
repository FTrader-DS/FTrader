package com.ftrader.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuizDetailResponse {
    private Long quizId;
    private Integer quizType;
    private Integer difficulty;
    private String sectorName;
    private String newsContent;
    private String question;
    private List<ChoiceDto> choices;
    private boolean solved;
    private boolean bookmarked;
    private List<VocabTermRef> vocabTerms;

    @Getter
    @AllArgsConstructor
    public static class ChoiceDto {
        private Long choiceId;
        private Integer choiceNo;
        private String content;
        // is_correct 제외 — 제출 후 SubmitResponse에서 correctChoiceId로 알려줌
    }
}
