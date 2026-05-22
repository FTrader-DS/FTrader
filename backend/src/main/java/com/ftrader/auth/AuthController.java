package com.ftrader.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${kakao.client-id}")
    private String kakaoClientId;

    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    /** 카카오 인가 페이지로 리다이렉트 */
    @GetMapping("/kakao")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
                + "?client_id=" + kakaoClientId
                + "&redirect_uri=" + URLEncoder.encode(kakaoRedirectUri, StandardCharsets.UTF_8)
                + "&response_type=code";
        response.sendRedirect(kakaoAuthUrl);
    }

    /** 카카오 인가 코드 수신 → JWT 발급 → 프론트로 리다이렉트 */
    @GetMapping("/kakao/callback")
    public void kakaoCallback(@RequestParam String code, HttpServletResponse response) throws IOException {
        try {
            String token = authService.kakaoLogin(code);
            response.sendRedirect(frontendUrl + "/auth/callback?token=" + token);
        } catch (Exception e) {
            log.error("Kakao OAuth callback failed", e);
            response.sendRedirect(frontendUrl + "/login?error=auth_failed");
        }
    }
}
