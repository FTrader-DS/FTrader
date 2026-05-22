package com.ftrader.vocabulary;

import com.ftrader.vocabulary.dto.UserVocabResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VocabularyMapper {

    Vocabulary findByTerm(@Param("term") String term);

    List<UserVocabResponse> findUserVocabularies(@Param("userId") Long userId);

    boolean existsUserVocab(@Param("userId") Long userId, @Param("vocabId") Long vocabId);

    void insertUserVocab(@Param("userId") Long userId, @Param("vocabId") Long vocabId);

    void deleteUserVocab(@Param("userVocabId") Long userVocabId, @Param("userId") Long userId);
}
