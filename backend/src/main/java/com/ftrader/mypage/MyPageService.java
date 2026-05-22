package com.ftrader.mypage;

import com.ftrader.mypage.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageMapper myPageMapper;

    public WrongAnswersResponse getWrongAnswers(Long userId) {
        List<WrongAnswerItem> items = myPageMapper.findWrongAnswers(userId);
        List<WrongAnswerTypeAnalysis> analysis = myPageMapper.findWrongAnswerAnalysis(userId);
        return new WrongAnswersResponse(items, analysis, items.size());
    }

    public List<SavedQuizItem> getSavedQuizzes(Long userId) {
        return myPageMapper.findSavedQuizzes(userId);
    }
}
