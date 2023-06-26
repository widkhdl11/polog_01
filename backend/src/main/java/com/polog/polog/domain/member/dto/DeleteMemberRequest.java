package com.polog.polog.domain.member.dto;

import com.polog.polog.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteMemberRequest {
    private Long uid;

    public static Member toEntity(DeleteMemberRequest request){
        return Member.builder()
                .uid(request.getUid())
                .build();
    }
}
