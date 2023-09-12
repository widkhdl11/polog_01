package com.polog.polog.domain.post.api;


import com.polog.polog.domain.category.application.CategoryService;
import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.member.application.MemberService;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.post.application.PostService;
import com.polog.polog.domain.post.domain.Post;
import com.polog.polog.domain.post.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Slf4j
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
    public PostingPostResponse posting(@RequestBody PostingPostRequest request) {
        System.out.println("포스팅 생성 실행");

        System.out.println(request);
        System.out.println(request.getContent());

        //Member member = memberService.findOneMember(request.getMember().getUid());
        Member member = memberService.findOneMember(1L);

        //Category category = categoryService.findOneCategory(request.getCategory().getUid());
        Category category = categoryService.findOneCategory(1L);

        Post post = PostingPostRequest.toEntity(request, member,category);

        Long uid = postService.posting(post);
        System.out.println("포스팅 생성 실행 완료");
        return new PostingPostResponse(uid);
    }
    /**
     * 포스트 수정
     * @param request
     * @return
     */
    @PatchMapping("/api/post/update")
    public PostingPostResponse postUpdate(@RequestBody UpdatePostRequest request) {
        System.out.println("수정 실행");

        postService.updatePost(UpdatePostRequest.toEntity(request));

        Post findPost = postService.findOne(request.getUid());
        Long uid = postService.posting(findPost);

        return new PostingPostResponse(uid);
    }

    /**
     * 포스트 목록
     * @param
     * @return
     */
    @GetMapping("api/posts/{memberId}")
    public List<PostListResponse> postList(@PathVariable("memberId") String memberId ) {
        System.out.println("포스트 목록 실행");

        List<Post> findPostList = postService.postList(memberId);

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


        return collect;

    }

    /**
     * 포스트 상세
     * @param uid
     * @param
     * @return
     */
    @GetMapping("/api/post/{uid}")
    public PostDto findOnePost(@PathVariable String uid){
        System.out.println("포스트 상세 실행");

        Long uid_l = Long.valueOf(uid);

        Post findPost = postService.findOne(uid_l);
        PostDto response = Post.toDto(findPost);

        System.out.println("포스트 상세 종료");
        return response;
    }

    /**
     * 포스트 삭제
     * @param
     */
    @DeleteMapping("/api/post/delete/{uid}")
    public void deletePost(@PathVariable String uid){
        System.out.println("포스트 삭제 실행");

        Post post = Post.builder()
                .uid(Long.valueOf(uid))
                .build();
        postService.deletePost(post);
        //postService.deletePost(DeletePostRequest.toEntity(request));

        System.out.println("포스트 삭제 종료");
    }

    


}
