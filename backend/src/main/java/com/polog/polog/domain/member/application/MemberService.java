package com.polog.polog.domain.member.application;

import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.member.dao.MemberRepository;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.member.dto.UpdateMemberRequest;
import com.polog.polog.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getUid();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findById(member.getId());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    /**
     * 회원 찾기
     */
    public Member findOneMember(Long uid) {
        Member member = memberRepository.findOne(uid);
        return member;
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void updateMember(Member member){

        Member findMember = memberRepository.findOne(member.getUid());
        findMember.changeMember(member);
        memberRepository.save(findMember);
    }

    /**
     * 회원 삭제
     */
    public void deleteMember(Member member) {
        // 회원 삭제를 위한 연관 포스트 삭제
        List<Post> findPostList = memberRepository.findIncludeCategory(member.getUid());
        for(Post post : findPostList){
            memberRepository.deletePost(post);
        }

        memberRepository.deleteByMember(member);
    }

    /**
     * 회원 목록
     */
    public List<Member> findAllMember(){
        return memberRepository.findAll();
    }
}
