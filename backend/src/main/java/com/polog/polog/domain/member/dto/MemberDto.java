package com.polog.polog.domain.member.dto;

import com.polog.polog.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MemberDto {

    private Long uid;
    private String id;
    private String password;
    private String email;

    public void setPassword(String password) { this.password = password; }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(id, password);
    }

    /**
     * MemberDto Member Entity로 변환
     * @param request
     * @return
     */
    public static Member toEntity(MemberDto request){
        return Member.builder()
                .uid(request.getUid())
                .id(request.getId())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();
    }

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .id(id)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
    }
}
