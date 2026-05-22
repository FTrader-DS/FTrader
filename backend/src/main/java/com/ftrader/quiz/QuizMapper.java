package com.ftrader.quiz;

import com.ftrader.quiz.dto.TodayQuizItem;
import com.ftrader.quiz.dto.VocabTermRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuizMapper {

    // ── 오늘의 퀴즈 ─────────────────────────────────────────
    List<TodayQuizItem> findUnsolvedSingles(@Param("userId") Long userId, @Param("limit") int limit);
    List<TodayQuizItem> findUnsolvedSets(@Param("userId") Long userId, @Param("limit") int limit);

    // ── 단일 퀴즈 ───────────────────────────────────────────
    Quiz findById(@Param("quizId") Long quizId);
    List<QuizChoice> findChoicesByQuizId(@Param("quizId") Long quizId);

    // ── 연쇄 퀴즈 세트 ─────────────────────────────────────
    QuizSet findSetById(@Param("setId") Long setId);
    List<Quiz> findQuizzesBySetId(@Param("setId") Long setId);

    // ── 답안 제출 ───────────────────────────────────────────
    boolean existsResult(@Param("userId") Long userId, @Param("quizId") Long quizId);
    Long findCorrectChoiceId(@Param("quizId") Long quizId);
    void insertResult(@Param("userId") Long userId,
                      @Param("quizId") Long quizId,
                      @Param("choiceId") Long choiceId,
                      @Param("isCorrect") boolean isCorrect);

    // ── 포인트 ──────────────────────────────────────────────
    void addPoints(@Param("userId") Long userId, @Param("points") int points);
    int findTotalPoints(@Param("userId") Long userId);

    // ── 북마크 ──────────────────────────────────────────────
    boolean existsBookmark(@Param("userId") Long userId, @Param("quizId") Long quizId);
    void insertBookmark(@Param("userId") Long userId, @Param("quizId") Long quizId);
    void deleteBookmark(@Param("userId") Long userId, @Param("quizId") Long quizId);

    // ── 용어 탐색 ────────────────────────────────────────────
    List<VocabTermRef> findVocabTermsInText(@Param("text") String text);
}
