package com.polog.polog.domain.post.dto;

import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.category.dto.CategoryDto;
import com.polog.polog.domain.category.dto.UpdateCategoryRequest;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.member.dto.MemberDto;
import com.polog.polog.domain.post.domain.Post;
import com.polog.polog.domain.post.domain.PostState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UpdatePostRequest {

    private Long uid;
    private String title;
    private String content;
    private CategoryDto category;
    private MemberDto member;
    private LocalDateTime editTime;
    private PostState state;

    /**
     * Update Post Request를 Entity로 변환
     */
    public static Post toEntity(UpdatePostRequest request){
        return Post.builder()
                .uid(request.getUid())
                .title(request.getTitle())
                .content(request.getContent())
                .editDate(request.getEditTime())
                .category(CategoryDto.ToEntity(request.getCategory()))
                .member(MemberDto.toEntity(request.getMember()))
                .build();
    }



}
