package com.polog.polog.global.auth.service;

import com.polog.polog.domain.member.dao.MemberRepository;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.member.dto.MemberRequestDto;
import com.polog.polog.domain.member.dto.MemberResponseDto;
import com.polog.polog.global.auth.jwt.TokenProvider;
import com.polog.polog.global.dto.token.TokenDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    //private final RedisService redisService;
    //private final RedisRepository redisRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public MemberResponseDto signup(MemberRequestDto requestDto) {

        if (memberRepository.opFindById(requestDto.getId()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = requestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    public TokenDto login(HttpServletRequest request, MemberRequestDto requestDto) {

        // 4. Redis에 RefreshToken 저장
        //TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
//
//        String refreshToken = tokenProvider.resolveRefreshToken(request);
//        refreshTokenRedisRepository.save(
//                RefreshToken.builder()
//                .id(authentication.getName())
//                .ip(Helper.getClientIp(request))
//                .authorities(authentication.getAuthorities())
//                .refreshToken(refreshToken)
//                .build());

        System.out.println("로그인 서비스 실행");

        return TokenDto.builder().build();
    }

    public void logout(String encryptedRefreshToken, String accessToken) {
        //this.verifiedRefreshToken(encryptedRefreshToken);
        //String refreshToken = Sha512DigestUtils.decryptAes(encryptedRefreshToken);
        //Claims claims = tokenProvider.parseClaims(refreshToken);
        //String id = claims.getSubject();
        //String redisRefreshToken = redisService.getValues(email);
//        if (redisService.checkExistsValue(redisRefreshToken)) {
//            redisService.deleteValues(email);
//
//            // 로그아웃 시 Access Token Redis 저장 ( key = Access Token / value = "logout" )
//            long accessTokenExpirationMillis = jwtTokenProvider.getAccessTokenExpirationMillis();
//            redisService.setValues(accessToken, "logout", Duration.ofMillis(accessTokenExpirationMillis));
//        }
    }

//    private void verifiedRefreshToken(String encryptedRefreshToken) {
//        if (encryptedRefreshToken == null) {
//            throw new BusinessLogicException(ExceptionCode.HEADER_REFRESH_TOKEN_NOT_EXISTS);
//        }
//    }

}