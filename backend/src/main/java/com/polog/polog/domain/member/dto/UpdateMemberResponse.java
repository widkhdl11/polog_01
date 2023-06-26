package com.polog.polog.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateMemberResponse {

    private Long uid;
    private String id;
    private String password;
    private String email;
}
