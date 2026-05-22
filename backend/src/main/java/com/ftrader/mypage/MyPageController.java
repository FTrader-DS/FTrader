package com.ftrader.mypage;

import com.ftrader.common.ApiResponse;
import com.ftrader.mypage.dto.SavedQuizItem;
import com.ftrader.mypage.dto.WrongAnswersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    /** 오답노트 — 오답 목록 + 유형 분석 */
    @GetMapping("/wrong-answers")
    public ResponseEntity<ApiResponse<WrongAnswersResponse>> getWrongAnswers(
            @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.success(myPageService.getWrongAnswers(userId)));
    }

    /** 나만의 문제집 — 북마크한 문제 목록 */
    @GetMapping("/saved-quiz")
    public ResponseEntity<ApiResponse<List<SavedQuizItem>>> getSavedQuizzes(
            @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(ApiResponse.success(myPageService.getSavedQuizzes(userId)));
    }
}
