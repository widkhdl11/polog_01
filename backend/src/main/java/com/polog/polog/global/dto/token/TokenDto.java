package com.polog.polog.global.dto.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenDto {
    private final String grantType;
    private final String authorizationType;
    private final String accessToken;
    private final String refreshToken;
    private final Long accessTokenExpiresIn;

}