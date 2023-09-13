package com.polog.polog.global.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polog.polog.domain.member.application.MemberService;
import com.polog.polog.global.config.AES128Config;
import com.polog.polog.global.dto.token.TokenDto;
import com.polog.polog.global.error.dto.LoginDto;
import com.polog.polog.global.redis.application.CustomUserDetailsService;
import com.polog.polog.global.redis.application.RedisService;
import com.polog.polog.global.redis.dao.CustomUserDetails;
import com.polog.polog.global.util.Responder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final AES128Config aes128Config;
    private final RedisService redisService;
    private final MemberService memberService;
    private final CustomUserDetailsService customUserDetailsService;



    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        // attemptAuthentication 이 메소드에서는 클라이언트로부터 전달된 사용자 이름과 비밀번호를 추출하고,
        // UsernamePasswordAuthenticationToken을 생성하여 사용자 인증을 시도합니다.
        // 사용자 이름과 비밀번호를 JWT로 인증하려면 여기에서 JWT 토큰을 생성하고 설정해야 합니다.

            // ServletInputStream을 LoginDto 객체로 역직렬화

            System.out.println("어템프트");
            ObjectMapper objectMapper = new ObjectMapper();
            LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);

            //Member member = memberService.findOneMemberId(loginDto.getId());
            CustomUserDetails user = (CustomUserDetails) customUserDetailsService.loadUserByUsername(loginDto.getId());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user,loginDto.getPassword(),null);

            setDetails(request, authenticationToken);
            return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

            System.out.println("서세스");
            System.out.println("request.getServletPath()1 : "+request.getServletPath());
            CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
            TokenDto tokenDto = tokenProvider.generateTokenDto(customUserDetails);
            String accessToken = tokenDto.getAccessToken();
            String refreshToken = tokenDto.getRefreshToken();
            String encryptedRefreshToken = aes128Config.encryptAes(refreshToken);
            tokenProvider.accessTokenSetHeader(accessToken, response);
            tokenProvider.refreshTokenSetHeader(encryptedRefreshToken, response);

            //Member findMember = memberService.findOneMemberId(customUserDetails.getId());

            Responder.loginSuccessResponse(response, customUserDetails);

            // 로그인 성공시 Refresh Token Redis 저장 ( key = Email / value = Refresh Token )
            //long refreshTokenExpirationMillis = tokenProvider.getRefreshTokenExpirationMillis();
            //redisService.setValues(findMember.getId(), refreshToken, Duration.ofMillis(refreshTokenExpirationMillis));

            redisService.setValuesObject(customUserDetails.getId(), refreshToken);

            this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
