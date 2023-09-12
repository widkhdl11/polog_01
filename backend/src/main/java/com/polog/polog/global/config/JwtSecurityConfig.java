package com.polog.polog.global.config;

import com.polog.polog.global.auth.jwt.TokenProvider;
import com.polog.polog.global.redis.application.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@RequiredArgsConstructor
@Slf4j
public class JwtSecurityConfig extends SecurityConfigurerAdapter <DefaultSecurityFilterChain, HttpSecurity>
         {
    private final TokenProvider tokenProvider;
    private final AES128Config aes128Config;
    private final RedisService redisService;


    @Override
    public void configure(HttpSecurity http) {

//        JwtFilter customFilter = new JwtFilter(tokenProvider);
//        JwtLoginFilter customLoginFilter = new JwtLoginFilter(
//                tokenProvider, aes128Config, redisService);
//
//        http.addFilterAt(customLoginFilter, UsernamePasswordAuthenticationFilter.class);
//        http.addFilterAfter(customFilter, UsernamePasswordAuthenticationFilter.class);
//        // JwtFilter를 BasicAuthenticationFilter 앞에 추가한다는 의미입니다
    }
}