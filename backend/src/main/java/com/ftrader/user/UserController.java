package com.ftrader.user;

import com.ftrader.common.ApiResponse;
import com.ftrader.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;

    /** 내 정보 + 레벨 + 통계 */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getMe(@AuthenticationPrincipal Long userId) {
        UserInfoResponse info = userMapper.findUserInfo(userId);
        return ResponseEntity.ok(ApiResponse.success(info));
    }
}
