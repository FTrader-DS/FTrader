package com.ftrader.vocabulary;

import com.ftrader.common.ApiResponse;
import com.ftrader.vocabulary.dto.SaveVocabularyRequest;
import com.ftrader.vocabulary.dto.UserVocabResponse;
import com.ftrader.vocabulary.dto.VocabularyDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VocabularyController {

    private final VocabularyService vocabularyService;

    /** 용어 뜻 조회 — 마이페이지 단어장 카드 팝업에서 호출 */
    @GetMapping("/api/vocabulary/{term}")
    public ResponseEntity<ApiResponse<VocabularyDetailResponse>> getByTerm(
            @PathVariable String term) {
        return ResponseEntity.ok(ApiResponse.success(vocabularyService.getByTerm(term)));
    }

    /** 내 단어장 목록 */
    @GetMapping("/api/user/vocabulary")
    public ResponseEntity<ApiResponse<List<UserVocabResponse>>> getUserVocabularies(
            @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.success(vocabularyService.getUserVocabularies(userId)));
    }

    /**
     * 단어장 저장 (퀴즈 중 클릭 시 호출)
     * 응답에 definition 미포함 — 힌트 방지
     */
    @PostMapping("/api/user/vocabulary")
    public ResponseEntity<ApiResponse<Void>> saveVocabulary(
            @RequestBody SaveVocabularyRequest request,
            @AuthenticationPrincipal Long userId) {
        vocabularyService.saveVocabulary(userId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /** 단어장 삭제 */
    @DeleteMapping("/api/user/vocabulary/{userVocabId}")
    public ResponseEntity<ApiResponse<Void>> deleteVocabulary(
            @PathVariable Long userVocabId,
            @AuthenticationPrincipal Long userId) {
        vocabularyService.deleteVocabulary(userVocabId, userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
