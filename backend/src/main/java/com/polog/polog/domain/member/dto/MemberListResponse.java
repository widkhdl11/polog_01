package com.polog.polog.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberListResponse {

    private Long uid;
    private String id;
    private String email;

}
