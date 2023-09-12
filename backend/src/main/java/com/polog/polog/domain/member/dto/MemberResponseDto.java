package com.polog.polog.domain.member.dto;

import com.polog.polog.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private String id;
    private String email;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .build();
    }
}