package com.polog.polog.domain.post.application;


import com.polog.polog.domain.category.application.CategoryService;
import com.polog.polog.domain.category.dao.CategoryRepository;
import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.category.dto.CategoryDto;
import com.polog.polog.domain.category.dto.UpdateCategoryRequest;
import com.polog.polog.domain.member.application.MemberService;
import com.polog.polog.domain.member.dao.MemberRepository;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.post.dao.PostRepository;
import com.polog.polog.domain.post.domain.Post;
import com.polog.polog.domain.post.dto.UpdatePostRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
// readonly true 일시 조회 성능 최적화
@RequiredArgsConstructor
// 생성자 Autowired를 위해 @AllArgsConstructor를 쓰다가
// 그보다 더 나은 final이 붙은 것만 생성자를 생성해주는
// @RequiredArgsConstructor 사용
public class PostService {

    // 생성자 Autowired를 위해 final 설정
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryService categoryService;


    /**
     * 포스트 등록
     * @param post
     * @return
     */
    @Transactional
    public Long posting(Post post){
        postRepository.save(post);
        return post.getUid();
    }

    /**
     * 포스트 목록
     * @param uid
     * @return
     */
    public List<Post> postList(Long uid){
        return postRepository.findAllByMember(uid);
    }


    /**
     * 포스트 수정
     * @param post
     */
    @Transactional
    public void updatePost(Post post){
        Member findMember = memberRepository.findOne(post.getMember().getUid());

        //등록한 member가 맞는지 검증

        // 해당 포스트 글을 찾아서
        // 포스트의 제목과 내용, 수정 날짜를 수정하고 request에 담긴 카테고리 uid를 가져와서 그것을 update

        Post findPost = postRepository.findOne(post.getUid());

        findPost.updatePost(post);

        postRepository.save(findPost);

    }

    /**
     * 포스트 하나 찾기
     * @param uid
     * @return
     */
    public Post findOne(Long uid) {
        Post findPost = postRepository.findOne(uid);
        return findPost;
    }


    /**
     * 포스트 삭제
     * @param post
     */
    public void deletePost(Post post){
        postRepository.delete(post);
    }

}
