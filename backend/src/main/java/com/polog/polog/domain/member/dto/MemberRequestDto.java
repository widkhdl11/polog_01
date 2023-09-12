package com.polog.polog.domain.member.dto;

import com.polog.polog.domain.member.domain.Authority;
import com.polog.polog.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {
    private String id;
    private String email;
    private String password;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .id(id)
                .email(email)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(id, password);
    }
}