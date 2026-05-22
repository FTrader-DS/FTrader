package com.ftrader.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WrongAnswersResponse {
    private List<WrongAnswerItem> items;
    private List<WrongAnswerTypeAnalysis> analysis;
    private int totalCount;
}
