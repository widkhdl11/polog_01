package com.polog.polog.global.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Slf4j
public class CustomAuthorityUtils {

    public static List<GrantedAuthority> createAuthorities(String role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }


//    public static void verifiedRole(String role) {
//        if (role == null) {
//            throw new BusinessLogicException(ExceptionCode.MEMBER_ROLE_DOES_NOT_EXISTS);
//        } else if (!role.equals(USER.toString()) && !role.equals(ADMIN.toString())) {
//            throw new BusinessLogicException(ExceptionCode.MEMBER_ROLE_INVALID);
//        }
//    }
}