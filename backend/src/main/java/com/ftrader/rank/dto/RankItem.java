package com.ftrader.rank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RankItem {
    private int ranking;
    private Long userId;
    private String nickname;
    private String profileImg;
    private int level;
    private int weeklyPoints;

    public String getLevelName() {
        return switch (level) {
            case 2 -> "주린이";
            case 3 -> "투자자";
            case 4 -> "고수";
            default -> "개미";
        };
    }
}
