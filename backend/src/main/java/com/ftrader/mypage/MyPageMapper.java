package com.ftrader.mypage;

import com.ftrader.mypage.dto.SavedQuizItem;
import com.ftrader.mypage.dto.WrongAnswerItem;
import com.ftrader.mypage.dto.WrongAnswerTypeAnalysis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyPageMapper {

    List<WrongAnswerItem> findWrongAnswers(@Param("userId") Long userId);

    List<WrongAnswerTypeAnalysis> findWrongAnswerAnalysis(@Param("userId") Long userId);

    List<SavedQuizItem> findSavedQuizzes(@Param("userId") Long userId);
}
