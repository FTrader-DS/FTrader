package com.ftrader.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuizSet {
    private Long setId;
    private Long sectorId;
    private String sectorName;   // JOIN from sector
    private String title;
    private String newsContent;
    private Boolean isActive;
}
