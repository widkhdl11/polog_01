package com.polog.polog.domain.post.dto;

import com.polog.polog.domain.category.dto.CategoryDto;
import com.polog.polog.domain.member.dto.MemberDto;
import com.polog.polog.domain.post.domain.PostState;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdatePostResponse {

    private Long uid;
    private String title;
    private String content;
    private CategoryDto category;
    private MemberDto member;
    private LocalDateTime editTime;
    private PostState state;

}
