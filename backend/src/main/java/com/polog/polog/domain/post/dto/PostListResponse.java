package com.polog.polog.domain.post.dto;

import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostListResponse {

    private Long uid;
    private String title;
    private String content;
    private Category category;
    private LocalDateTime regDate;
    private LocalDateTime editDate;

}
