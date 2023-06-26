package com.polog.polog.domain.member.api;

import com.polog.polog.domain.member.application.MemberService;
import com.polog.polog.domain.member.dao.MemberRepository;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.member.dto.UpdateMemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberControllerTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원가입 테스트")
    void 회원가입() throws Exception {
        Member member = Member.builder()
                .id("kim")
                .password("1234")
                .email("23@na.com")
                .build();

        Long savedId = memberService.join(member);
        memberRepository.findOne(savedId);

        assertEquals(member, memberRepository.findOne(savedId));

    }

    @Test
    @DisplayName("회원 정보 수정")
    void 회원정보수정() throws Exception{
        //give
        Member member = Member.builder()
                .id("kim")
                .password("1234")
                .email("23@na.com")
                .build();

        Long savedId = memberService.join(member);

        //when
        UpdateMemberRequest updateMemberRequest = UpdateMemberRequest.builder()
                .uid(savedId)
                .id("sung")
                .password("4321")
                .email("wq@naver.com")
                .build();

        Member member2 = UpdateMemberRequest.dtoToEntity(updateMemberRequest);
        memberService.updateMember(member2);


        Member findMember = memberRepository.findOne(savedId);
        //then
        assertEquals(findMember.getId(), "sung");
        assertEquals(findMember.getPassword(), "4321");
        assertEquals(findMember.getEmail(), "wq@naver.com");


    }

    @Test
    @DisplayName("회원 삭제")
    void 회원삭제() throws Exception{
        //given
        Member findMember = memberService.findOneMember(1L);

        //when
        memberService.deleteMember(findMember);

        //then
        List<Member> findMemberList = memberService.findAllMember();

        assertEquals(findMemberList.size(),0);

    }
}