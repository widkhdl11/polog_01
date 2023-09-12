package com.polog.polog.domain.member.dto;

import com.polog.polog.domain.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateMemberRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String password;

    @NotBlank
    private String check_password;

    @NotBlank
    @Email
    private String email;

    private static Member toEntity(CreateMemberRequest request){
        return Member.builder()
                .id(request.getId())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();
    }

}
