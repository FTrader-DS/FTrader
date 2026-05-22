package com.ftrader.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private Long userId;
    private String nickname;
    private String profileImg;

    // 레벨 정보
    private int points;
    private int level;
    private int streak;

    // 통계
    private long totalSolved;
    private long correctCount;

    public String getLevelName() {
        return switch (level) {
            case 2 -> "주린이";
            case 3 -> "투자자";
            case 4 -> "고수";
            default -> "개미";
        };
    }

    public double getAccuracy() {
        if (totalSolved == 0) return 0.0;
        return Math.round((double) correctCount / totalSolved * 1000) / 10.0; // 소수점 1자리 %
    }
}
