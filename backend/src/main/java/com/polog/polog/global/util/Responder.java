package com.polog.polog.global.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.global.common.error.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;

public class Responder {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static void loginSuccessResponse(HttpServletResponse response, Member findMember) throws IOException {

        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(findMember));

    }
    public static void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {

        response.setStatus(errorCode.getStatus());
        response.getOutputStream().write(objectMapper.writeValueAsBytes(errorCode.getMessage()));

    }
}
