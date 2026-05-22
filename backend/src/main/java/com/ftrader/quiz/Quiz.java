package com.ftrader.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    private Long quizId;
    private Long sectorId;
    private String sectorName;   // JOIN from sector
    private Integer quizType;
    private Integer difficulty;
    private String newsContent;
    private String question;
    private String explanation;
    private Boolean isActive;
}
