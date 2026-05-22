package com.ftrader.quiz;

import com.ftrader.quiz.dto.*;
import com.ftrader.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private static final int SINGLE_COUNT = 7;
    private static final int SET_COUNT    = 1;
    private static final int CORRECT_PTS  = 10;
    private static final int WRONG_PTS    = 2;

    private final QuizMapper quizMapper;
    private final UserMapper userMapper;

    // ── 오늘의 퀴즈 ────────────────────────────────────────────

    public List<TodayQuizItem> getTodayQuiz(Long userId) {
        List<TodayQuizItem> result = new ArrayList<>();
        result.addAll(quizMapper.findUnsolvedSingles(userId, SINGLE_COUNT));
        result.addAll(quizMapper.findUnsolvedSets(userId, SET_COUNT));
        Collections.shuffle(result);
        return result;
    }

    // ── 단일 퀴즈 상세 ─────────────────────────────────────────

    public QuizDetailResponse getQuiz(Long quizId, Long userId) {
        Quiz quiz = quizMapper.findById(quizId);
        if (quiz == null) {
            throw new IllegalArgumentException("존재하지 않는 문제입니다.");
        }
        return buildDetailResponse(quiz, userId);
    }

    // ── 연쇄 퀴즈 세트 ─────────────────────────────────────────

    public QuizSetResponse getQuizSet(Long setId, Long userId) {
        QuizSet set = quizMapper.findSetById(setId);
        if (set == null) {
            throw new IllegalArgumentException("존재하지 않는 세트입니다.");
        }

        List<Quiz> quizzes = quizMapper.findQuizzesBySetId(setId);
        List<QuizDetailResponse> details = quizzes.stream()
                .map(q -> buildSetItemResponse(q, set, userId))
                .toList();

        List<VocabTermRef> vocabTerms = quizMapper.findVocabTermsInText(
                StringUtils.hasText(set.getNewsContent()) ? set.getNewsContent() : "");

        return QuizSetResponse.builder()
                .setId(set.getSetId())
                .sectorName(set.getSectorName())
                .title(set.getTitle())
                .newsContent(set.getNewsContent())
                .vocabTerms(vocabTerms)
                .quizzes(details)
                .build();
    }

    // ── 답안 제출 ──────────────────────────────────────────────

    @Transactional
    public SubmitResponse submit(Long quizId, Long choiceId, Long userId) {
        if (quizMapper.existsResult(userId, quizId)) {
            throw new IllegalArgumentException("이미 제출한 문제입니다.");
        }

        Long correctChoiceId = quizMapper.findCorrectChoiceId(quizId);
        boolean correct = correctChoiceId.equals(choiceId);

        quizMapper.insertResult(userId, quizId, choiceId, correct);

        int earned = correct ? CORRECT_PTS : WRONG_PTS;
        quizMapper.addPoints(userId, earned);
        int total = quizMapper.findTotalPoints(userId);

        // 오늘 첫 제출이면 스트릭 갱신 (0 반환 = 이미 오늘 출석)
        boolean newAttend = userMapper.updateStreak(userId) > 0;
        int streak = userMapper.findStreak(userId);

        Quiz quiz = quizMapper.findById(quizId);

        return SubmitResponse.builder()
                .correct(correct)
                .correctChoiceId(correctChoiceId)
                .explanation(quiz.getExplanation())
                .earnedPoints(earned)
                .totalPoints(total)
                .streak(streak)
                .newAttend(newAttend)
                .build();
    }

    // ── 북마크 토글 ───────────────────────────────────────────

    @Transactional
    public BookmarkResponse toggleBookmark(Long quizId, Long userId) {
        if (quizMapper.existsBookmark(userId, quizId)) {
            quizMapper.deleteBookmark(userId, quizId);
            return new BookmarkResponse(false);
        }
        quizMapper.insertBookmark(userId, quizId);
        return new BookmarkResponse(true);
    }

    // ── 내부 헬퍼 ─────────────────────────────────────────────

    private QuizDetailResponse buildDetailResponse(Quiz quiz, Long userId) {
        List<QuizDetailResponse.ChoiceDto> choices = quizMapper.findChoicesByQuizId(quiz.getQuizId())
                .stream()
                .map(c -> new QuizDetailResponse.ChoiceDto(c.getChoiceId(), c.getChoiceNo(), c.getContent()))
                .toList();

        String searchText = (StringUtils.hasText(quiz.getNewsContent()) ? quiz.getNewsContent() : "")
                + " " + quiz.getQuestion();
        List<VocabTermRef> vocabTerms = quizMapper.findVocabTermsInText(searchText);

        return QuizDetailResponse.builder()
                .quizId(quiz.getQuizId())
                .quizType(quiz.getQuizType())
                .difficulty(quiz.getDifficulty())
                .sectorName(quiz.getSectorName())
                .newsContent(quiz.getNewsContent())
                .question(quiz.getQuestion())
                .choices(choices)
                .solved(quizMapper.existsResult(userId, quiz.getQuizId()))
                .bookmarked(quizMapper.existsBookmark(userId, quiz.getQuizId()))
                .vocabTerms(vocabTerms)
                .build();
    }

    // 세트 내 문제는 뉴스가 세트 공통이므로 newsContent를 세트에서 가져옴
    private QuizDetailResponse buildSetItemResponse(Quiz quiz, QuizSet set, Long userId) {
        List<QuizDetailResponse.ChoiceDto> choices = quizMapper.findChoicesByQuizId(quiz.getQuizId())
                .stream()
                .map(c -> new QuizDetailResponse.ChoiceDto(c.getChoiceId(), c.getChoiceNo(), c.getContent()))
                .toList();

        return QuizDetailResponse.builder()
                .quizId(quiz.getQuizId())
                .quizType(quiz.getQuizType())
                .difficulty(quiz.getDifficulty())
                .sectorName(set.getSectorName())
                .newsContent(set.getNewsContent())
                .question(quiz.getQuestion())
                .choices(choices)
                .solved(quizMapper.existsResult(userId, quiz.getQuizId()))
                .bookmarked(quizMapper.existsBookmark(userId, quiz.getQuizId()))
                .build();
    }
}
