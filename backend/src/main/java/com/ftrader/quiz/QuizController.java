package com.ftrader.quiz;

import com.ftrader.common.ApiResponse;
import com.ftrader.quiz.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    /** 오늘의 퀴즈 목록 (안 푼 문제 랜덤 혼합) */
    @GetMapping("/today")
    public ResponseEntity<ApiResponse<List<TodayQuizItem>>> getToday(
            @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.success(quizService.getTodayQuiz(userId)));
    }

    /** 단일 퀴즈 상세 (선택지 포함, 정답 미포함) */
    @GetMapping("/{quizId}")
    public ResponseEntity<ApiResponse<QuizDetailResponse>> getQuiz(
            @PathVariable Long quizId,
            @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.success(quizService.getQuiz(quizId, userId)));
    }

    /** 연쇄 퀴즈 세트 상세 (뉴스 카드 + 문제 목록) */
    @GetMapping("/set/{setId}")
    public ResponseEntity<ApiResponse<QuizSetResponse>> getSet(
            @PathVariable Long setId,
            @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.success(quizService.getQuizSet(setId, userId)));
    }

    /** 답안 제출 → 정오답 + 해설 + 포인트 반환 */
    @PostMapping("/{quizId}/submit")
    public ResponseEntity<ApiResponse<SubmitResponse>> submit(
            @PathVariable Long quizId,
            @RequestBody SubmitRequest request,
            @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.success(
                quizService.submit(quizId, request.getChoiceId(), userId)));
    }

    /** 북마크 토글 (저장 ↔ 해제) */
    @PostMapping("/{quizId}/bookmark")
    public ResponseEntity<ApiResponse<BookmarkResponse>> bookmark(
            @PathVariable Long quizId,
            @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.success(quizService.toggleBookmark(quizId, userId)));
    }
}
