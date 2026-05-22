package com.ftrader.vocabulary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vocabulary {
    private Long vocabId;
    private String term;
    private String definition;
    private String example;
    private String category;
    private Long sectorId;
}
