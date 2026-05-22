package com.ftrader.auth;

import com.ftrader.auth.dto.KakaoTokenResponse;
import com.ftrader.auth.dto.KakaoUserInfoResponse;
import com.ftrader.jwt.JwtProvider;
import com.ftrader.user.User;
import com.ftrader.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoApiClient kakaoApiClient;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;

    @Transactional
    public String kakaoLogin(String code) {
        KakaoTokenResponse kakaoToken = kakaoApiClient.getToken(code);
        KakaoUserInfoResponse kakaoUser = kakaoApiClient.getUserInfo(kakaoToken.getAccessToken());

        String kakaoId = String.valueOf(kakaoUser.getId());
        String nickname = kakaoUser.getKakaoAccount().getProfile().getNickname();
        String profileImg = kakaoUser.getKakaoAccount().getProfile().getProfileImageUrl();

        User user = userMapper.findByKakaoId(kakaoId);
        if (user == null) {
            user = User.builder()
                    .kakaoId(kakaoId)
                    .nickname(nickname)
                    .profileImg(profileImg)
                    .build();
            userMapper.insert(user);
            userMapper.insertUserLevel(user.getUserId());
        }

        return jwtProvider.createToken(user.getUserId());
    }
}
