package com.ftrader.vocabulary.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/** POST /api/user/vocabulary — 퀴즈 중 단어 저장 (뜻은 응답에 포함하지 않음) */
@Getter
@NoArgsConstructor
public class SaveVocabularyRequest {
    private Long vocabId;
}
