package com.ftrader.user;

import com.ftrader.user.dto.UserInfoResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByKakaoId(String kakaoId);
    User findById(Long userId);
    void insert(User user);
    void insertUserLevel(Long userId);
    UserInfoResponse findUserInfo(Long userId);

    // 오늘 첫 제출 시에만 스트릭 갱신. 반환값: 1=갱신됨, 0=오늘 이미 출석
    int updateStreak(Long userId);
    int findStreak(Long userId);
}
