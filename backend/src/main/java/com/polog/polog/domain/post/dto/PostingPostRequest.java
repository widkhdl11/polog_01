package com.polog.polog.domain.post.dto;


import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.post.domain.PostState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PostingPostRequest {


    private Long uid;

    private String title;

    private String content;

    private LocalDateTime regDate;

    //private LocalDateTime editDate;

    private PostState state;

    private Member member;

    private Category category;

//    private Long memberUid;
//    private Long categoryUid;

}
