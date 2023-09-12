package com.polog.polog.domain.post.dto;


import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.post.domain.Post;
import com.polog.polog.domain.post.domain.PostState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class PostingPostRequest {


    private String title;

    private String content;

    private LocalDateTime regDate;

    //private LocalDateTime editDate;

    private PostState state;

    private Member member;

    private Category category;

//    private Long memberUid;
//    private Long categoryUid;

    public static Post toEntity(PostingPostRequest request, Member member, Category category){
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .regDate(LocalDateTime.now())
                .member(member)
                .category(category)
                .state(PostState.COMP)
                .editDate(null)
                .build();
    }
}
