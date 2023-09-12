package com.polog.polog.global.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polog.polog.global.common.error.ErrorResponse;
import com.polog.polog.global.common.error.exception.BusinessException;
import com.polog.polog.global.redis.application.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter { //필터링을 실행하는 메소드다.
//    resolveToken을 통해 토큰 정보를 꺼내온 다음, validateToken으로 토큰이 유효한지 검사를 해서,
//    만약 유효하다면 Authentication을 가져와서 SecurityContext에 저장한다.
//    SecurityContext에서 허가된 uri 이외의 모든 Request 요청은 전부 이 필터를 거치게 되며, 토큰 정보가 없거나 유효치않으면 정상적으로 수행되지 않는다.
//    반대로 Request가 정상적으로 Controller까지 도착했으면 SecurityContext에 Member ID가 존재한다는 것이 보장이 된다.

    // JWT를 사용한 로그인 작업에서 OncePerRequestFilter는 주로 다음 메소드를 구현하게 됩니다:
    // doFilterInternal 이 메소드에서는 클라이언트의 HTTP 요청을 가로채고 JWT 토큰을 추출하고, 토큰의 유효성을 검사합니다.
    // JWT 토큰이 유효한 경우, Authentication 객체를 생성하고, 이를 SecurityContext에 저장하여 사용자를 인증합니다.
    // 이 메소드는 모든 요청에 대해 실행되므로 JWT 토큰의 검증과 인증 작업을 이곳에서 수행합니다.

    private static final List<String> EXCLUDE_URL =
            List.of("/",
                    "/h2",
                    "/members/signup",
                    "/api/login",
                    "/auth/reissue");
    private final TokenProvider jwtTokenProvider;
    private final RedisService redisService;

    // JWT 인증 정보를 현재 쓰레드의 SecurityContext에 저장(가입/로그인/재발급 Request 제외)
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = jwtTokenProvider.resolveAccessToken(request);
            if (StringUtils.hasText(accessToken) && doNotLogout(accessToken)
                    && jwtTokenProvider.validateToken(accessToken, response)) {
                setAuthenticationToContext(accessToken);
            }
            // TODO: 예외처리 리팩토링
        } catch (RuntimeException e) {
            if (e instanceof BusinessException) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(ErrorResponse.of( ((BusinessException) e).getErrorCode()) );
                response.getWriter().write(json);
                response.setStatus(((BusinessException) e).getErrorCode().getStatus());
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean doNotLogout(String accessToken) {
        String isLogout = redisService.getValues(accessToken);
        return isLogout.equals("false");
    }

    // EXCLUDE_URL과 동일한 요청이 들어왔을 경우, 현재 필터를 진행하지 않고 다음 필터 진행
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean result = EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
        System.out.println("request.getServletPath() : "+request.getServletPath());

        return result;
    }

    private void setAuthenticationToContext(String accessToken) {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("# Token verification success!");
    }
}