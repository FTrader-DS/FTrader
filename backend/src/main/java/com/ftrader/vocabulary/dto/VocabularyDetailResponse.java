package com.ftrader.vocabulary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** GET /api/vocabulary/{term} — 용어 뜻 상세 (마이페이지 단어장에서 사용) */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyDetailResponse {
    private Long vocabId;
    private String term;
    private String definition;
    private String example;
    private String category;
}
