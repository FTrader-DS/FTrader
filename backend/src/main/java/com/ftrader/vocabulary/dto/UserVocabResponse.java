package com.ftrader.vocabulary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/** GET /api/user/vocabulary — 내 단어장 목록 항목 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserVocabResponse {
    private Long userVocabId;
    private Long vocabId;
    private String term;
    private String definition;
    private String example;
    private String category;
    private LocalDateTime savedAt;
}
