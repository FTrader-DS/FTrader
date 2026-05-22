package com.ftrader.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;
    private String kakaoId;
    private String nickname;
    private String profileImg;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
