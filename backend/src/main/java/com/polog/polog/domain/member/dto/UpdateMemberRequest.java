package com.polog.polog.domain.member.dto;


import com.polog.polog.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UpdateMemberRequest {

    private Long uid;
    private String id;
    private String password;
    private String email;

    public static Member dtoToEntity(UpdateMemberRequest request){
        Member member = Member.builder()
                .uid(request.getUid())
                .id(request.getId())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();
        return member;
    }

    public static UpdateMemberRequest entityToDto(Member member){
        UpdateMemberRequest updateMemberRequest = UpdateMemberRequest.builder()
                .uid(member.getUid())
                .id(member.getId())
                .password(member.getPassword())
                .email(member.getEmail())
                .build();
        return updateMemberRequest;
    }

}


