package com.polog.polog.domain.post.domain;

import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.category.dto.CategoryDto;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.post.dto.PostDto;
import com.polog.polog.domain.post.dto.PostingPostRequest;
import com.polog.polog.domain.post.dto.UpdatePostRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="posts_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 기본 생성자를 생성하는 어노테이션입니다.
// 기본 생성자에 접근 제어자를 설정하지 않으면
// 무분별하게 객체가 생성될 수 있기 때문에
// access 속성을 통해 설정합니다.
@AllArgsConstructor(access = AccessLevel.PRIVATE)
// 모든 멤버변수를 가지고 있는 생성자를 생성합니다.
@Builder
@EqualsAndHashCode
public class Post {

    @Id @GeneratedValue
    @Column(name="post_uid")
    private Long uid;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="content", nullable = false)
    private String content;

    private LocalDateTime regDate;

    private LocalDateTime editDate;

    @Enumerated(EnumType.STRING)
    private PostState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_uid")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_uid")
    private Category category;



    @Builder
    public Post (String title, String content, Member member, Category category){

        this.title = title;
        this.content = content;
        this.regDate = LocalDateTime.now();
        this.editDate = null;
        this.state = PostState.COMP;
        this.member = member;
        this.category = category;

    }
    //== 생성 매서드 ==//
    public static Post createPost(Member member, Category category, String title, String content){
        Post post = new Post();
        post.builder()
                .title(title)
                .content(content)
                .regDate(LocalDateTime.now())
                .editDate(null)
                .member(member)
                .category(category)
                .state(PostState.COMP);
        return post;
    }

    public static PostDto toDto(Post post){
        return PostDto.builder()
                .uid(post.getUid())
                .title(post.getTitle())
                .content(post.getContent())
                .member(Member.toDto(post.getMember()))
                .category(Category.toDto(post.getCategory()))
                .build();
    }

    //=== 비즈니스 로직 ===

    /**
     * 포스트 업데이트
     * @param post
     */
    public void updatePost(Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
        this.regDate = post.getRegDate();
        this.editDate = LocalDateTime.now();
        this.member = post.getMember();
        this.category = post.getCategory();
        this.state = PostState.COMP;
    }




}
