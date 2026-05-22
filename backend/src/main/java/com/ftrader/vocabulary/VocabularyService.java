package com.ftrader.vocabulary;

import com.ftrader.vocabulary.dto.SaveVocabularyRequest;
import com.ftrader.vocabulary.dto.UserVocabResponse;
import com.ftrader.vocabulary.dto.VocabularyDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VocabularyService {

    private final VocabularyMapper vocabularyMapper;

    /** 용어 상세 조회 (마이페이지 단어장 카드에서 사용) */
    public VocabularyDetailResponse getByTerm(String term) {
        Vocabulary vocab = vocabularyMapper.findByTerm(term);
        if (vocab == null) {
            throw new IllegalArgumentException("존재하지 않는 용어입니다.");
        }
        return new VocabularyDetailResponse(
                vocab.getVocabId(), vocab.getTerm(),
                vocab.getDefinition(), vocab.getExample(), vocab.getCategory());
    }

    /** 내 단어장 목록 */
    public List<UserVocabResponse> getUserVocabularies(Long userId) {
        return vocabularyMapper.findUserVocabularies(userId);
    }

    /** 단어장 저장 — 뜻은 응답에 포함하지 않음 (퀴즈 힌트 방지) */
    @Transactional
    public void saveVocabulary(Long userId, SaveVocabularyRequest request) {
        if (vocabularyMapper.existsUserVocab(userId, request.getVocabId())) {
            return; // 이미 저장된 단어면 무시
        }
        vocabularyMapper.insertUserVocab(userId, request.getVocabId());
    }

    /** 단어장 삭제 */
    @Transactional
    public void deleteVocabulary(Long userVocabId, Long userId) {
        vocabularyMapper.deleteUserVocab(userVocabId, userId);
    }
}
