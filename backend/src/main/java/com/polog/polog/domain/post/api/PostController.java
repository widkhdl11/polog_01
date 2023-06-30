package com.polog.polog.domain.post.api;


import com.polog.polog.domain.category.application.CategoryService;
import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.member.application.MemberService;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.post.application.PostService;
import com.polog.polog.domain.post.domain.Post;
import com.polog.polog.domain.post.domain.PostState;
import com.polog.polog.domain.post.dto.*;
import com.polog.polog.global.util.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final CategoryService categoryService;


    /**
     * 포스트 생성
     * @param request
     * @return
     */
    @PostMapping("/api/post/new")
    public PostingPostResponse posting(@RequestBody @Valid PostingPostRequest request) {


        Member member = memberService.findOneMember(request.getMember().getUid());

        Category category = categoryService.findOneCategory(request.getCategory().getUid());

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .regDate(LocalDateTime.now())
                .member(member)
                .category(category)
                .state(PostState.COMP)
                .editDate(null)
                .build();

        Long uid = postService.posting(post);

        return new PostingPostResponse(uid);
    }
    /**
     * 포스트 수정
     * @param request
     * @return
     */
    @PutMapping("api/post/update")
    public PostingPostResponse postUpdate(@RequestBody UpdatePostRequest request) {


        postService.updatePost(UpdatePostRequest.toEntity(request));

        Post findPost = postService.findOne(request.getUid());
        Long uid = postService.posting(findPost);

        return new PostingPostResponse(uid);
    }

    /**
     * 포스트 목록
     * @param request
     * @return
     */
    @PostMapping("api/posts/")
    public Result<PostingPostResponse> postList(@RequestBody @Valid PostingPostRequest request) {

        List<Post> findPostList = postService.postList(request.getMember().getUid());

        List<PostListResponse> collect = findPostList.stream()
                .map(p -> new PostListResponse(
                        p.getUid(),
                        p.getTitle(),
                        p.getContent(),
                        p.getCategory(),
                        p.getRegDate(),
                        p.getEditDate()
                        ))
                .collect(Collectors.toList());


        return new Result(collect);

    }

    /**
     * 포스트 상세
     * @param uid
     * @param request
     * @return
     */
    @PutMapping("/api/post/{uid}")
    public Post findOnePost(@PathVariable Long uid, @RequestBody @Valid UpdatePostRequest request){

        Post findPost = postService.findOne(uid);

        return findPost;
    }

    /**
     * 포스트 삭제
     * @param request
     */
    @DeleteMapping("/api/post/delete/{uid}")
    public void deletePost(@PathVariable Long uid, @RequestBody DeletePostRequest request){

        postService.deletePost(DeletePostRequest.toEntity(request));

    }

    


}
