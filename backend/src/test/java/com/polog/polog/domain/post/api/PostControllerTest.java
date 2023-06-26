package com.polog.polog.domain.post.api;

import com.polog.polog.domain.category.application.CategoryService;
import com.polog.polog.domain.category.dao.CategoryRepository;
import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.member.application.MemberService;
import com.polog.polog.domain.member.dao.MemberRepository;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.post.application.PostService;
import com.polog.polog.domain.post.dao.PostRepository;
import com.polog.polog.domain.post.domain.Post;
import com.polog.polog.domain.post.domain.PostState;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostControllerTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;




    @Test
    @DisplayName("포스팅 테스트")
    void 포스팅() throws Exception{
        //given
        Member member = Member.builder()
                .id("kim")
                .password("1234")
                .email("23@na.com")
                .build();

        //when
        Long savedMemberUid = memberService.join(member);
        Member findMember = memberRepository.findOne(savedMemberUid);

        Category category = Category.builder()
                .name("카테고리1")
                .parentUid(1L)
                .order(0)
                .build();

        //when
        Long savedCategoryUid = categoryService.createCategory(category);
        Category findCategory = categoryRepository.findOne(savedCategoryUid);

        Post post = Post.createPost(findMember,findCategory,"첫글 제목", "첫글 내용");

        Long uid = postService.posting(post);
        Post findPost = postRepository.findOne(uid);

        //then
        assertEquals(post, findPost);
    }

    @Test
    @DisplayName("포스트 하나 가져오기")
    void 포스트하나가져오기() throws Exception{
        //given
        Member member = Member.builder()
                .id("kim")
                .password("1234")
                .email("23@na.com")
                .build();

        Category category = Category.builder()
                .name("카테고리1")
                .parentUid(1L)
                .order(0)
                .build();

        //Post post = Post.createPost(findMember,findCategory,"첫글 제목", "첫글 내용");

        Post post = Post.builder()
                .title("첫글 제목")
                .content("첫글 내용")
                .member(member)
                .category(category)
                .build();

        Long uid = postService.posting(post);
        Post findPost = postRepository.findOne(uid);

        //then

        assertEquals(findPost,post);
    }

    @Test
    @DisplayName("포스트 리스트")
    void 포스트리스트() throws Exception{

        //given
        Member member = Member.builder()
                .id("kim")
                .password("1234")
                .email("23@na.com")
                .build();



        Category category = Category.builder()
                .name("카테고리1")
                .parentUid(1L)
                .order(0)
                .build();

        //Post post = Post.createPost(findMember,findCategory,"첫글 제목", "첫글 내용");

        Post post = Post.builder()
                .title("첫글 제목")
                .content("첫글 내용")
                .member(member)
                .category(category)
                .build();

        List<Post> postList = new ArrayList<>();
        postList.add(post);

        Long savedMemberUid = memberService.join(member);
        List<Post> findPostList = postRepository.findAllByMember(savedMemberUid);

        //then

        assertEquals(findPostList.size(),1);
        assertIterableEquals(findPostList,postList);

    }

    @Test
    @DisplayName("포스트 삭제")
    void 포스트_삭제() throws Exception{
        //given
        Post findPost = postRepository.findOne(1L);
        List<Post> findPostList = postRepository.findAllByMember(1L);

        //when
        postService.deletePost(findPost);

        List<Post> findPostList2 = postRepository.findAllByMember(1L);

        //then
        assertEquals(findPostList2.size(),0);
    }

}