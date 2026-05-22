package com.ftrader.user;

import com.ftrader.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public UserInfoResponse getMyInfo(Long userId){
        UserInfoResponse info = userMapper.findUserInfo(userId);
        if(info == null){
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
        return info;
    }
}
