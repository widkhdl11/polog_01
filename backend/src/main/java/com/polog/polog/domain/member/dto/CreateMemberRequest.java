package com.polog.polog.domain.member.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class CreateMemberRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String password;

    @NotBlank
    @Email
    private String email;

}
