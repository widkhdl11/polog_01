package com.polog.polog.domain.post.dto;

import com.polog.polog.domain.category.dto.CategoryDto;
import com.polog.polog.domain.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostDto {

    private Long uid;
    private String title;
    private String content;

    private MemberDto member;
    private CategoryDto category;
    
}
