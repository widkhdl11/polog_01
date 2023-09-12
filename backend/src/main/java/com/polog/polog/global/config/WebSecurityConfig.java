package com.polog.polog.global.config;

import com.polog.polog.domain.member.application.MemberService;
import com.polog.polog.global.auth.jwt.*;
import com.polog.polog.global.redis.application.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
//@Component
@Slf4j
//@EnableMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final AES128Config aes128Config;
    private final RedisService redisService;
    private final MemberService memberService;

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
//
//
//        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(authenticationManager,
//                tokenProvider, aes128Config, redisService, memberService);
//
//        JwtFilter jwtFilter = new JwtFilter(tokenProvider, redisService);
//        jwtLoginFilter.setFilterProcessesUrl("/api/login");
//        jwtLoginFilter.setAuthenticationManager(authenticationManager);

        http
                .headers().frameOptions().sameOrigin()

                .and()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)


                .and()
//                .addFilterBefore(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterAfter(jwtFilter, JwtLoginFilter.class)
                .apply(new CustomFilterConfigurer())

                .and()
                .authorizeHttpRequests(authorizeHttpRequests->authorizeHttpRequests
                    .requestMatchers("/api/**").permitAll()
                    .requestMatchers(PathRequest.toH2Console()).permitAll()
                    .anyRequest().authenticated()
                );


//                .formLogin(login->
//                        login.loginPage("/api/login")
//                                .loginProcessingUrl("/loginprocess")
//                                .permitAll()
//                                .defaultSuccessUrl("/", false)
//                                .failureUrl("/login-error")
//                );
//                .addFilterAt(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {

            log.info("SecurityConfiguration.CustomFilterConfigurer.configure excute");

            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(authenticationManager,
                    tokenProvider, aes128Config, redisService, memberService);

            JwtFilter jwtFilter = new JwtFilter(tokenProvider, redisService);
            jwtLoginFilter.setFilterProcessesUrl("/api/login");

            builder
                    .addFilter(jwtLoginFilter)
                    .addFilterAfter(jwtFilter, JwtLoginFilter.class);
        }
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Refresh"));
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Refresh");
        configuration.addAllowedHeader("*");
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}