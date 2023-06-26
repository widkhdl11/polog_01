package com.polog.polog.domain.member.dto;

import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.category.dto.CategoryDto;
import com.polog.polog.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberDto {

    private Long uid;
    private String id;
    private String password;
    private String email;

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
}
