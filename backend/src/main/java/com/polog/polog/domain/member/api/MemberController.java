package com.polog.polog.domain.member.api;


import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.category.dto.CreateCategoryListResponse;
import com.polog.polog.domain.category.dto.DeleteCategoryRequest;
import com.polog.polog.domain.member.application.MemberService;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.member.dto.*;
import com.polog.polog.global.util.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 등록
     * @param request
     * @return
     */
    @PostMapping("/api/member/join")
    public CreateMemberResponse joinMember(@RequestBody @Valid CreateMemberRequest request) {

        Member member = Member.builder()
                .id(request.getId())
                .password(request.getPassword())
                .email((request.getEmail()))
                .build();

        Long uid = memberService.join(member);
        return new CreateMemberResponse(uid);
    }

    /**
     * 회원 수정
     * @param
     * @param request
     * @return
     */
    @PutMapping("/api/member/update/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id") String id, @RequestBody @Valid UpdateMemberRequest request) {

        Member member = UpdateMemberRequest.dtoToEntity(request);
        memberService.updateMember(member);

        Member findMember = memberService.findOneMember(request.getUid());
        return new UpdateMemberResponse(findMember.getUid(), findMember.getId(), findMember.getPassword(), findMember.getEmail());
    }



    /**
     * 회원 목록
     */
    @PostMapping("/api/members")
    public Result<MemberListResponse> findAllMember(){
        List<Member> memberList = memberService.findAllMember();

        List<MemberListResponse> collect = memberList.stream()
                .map(c -> new MemberListResponse(
                        c.getUid(),
                        c.getId(),
                        c.getEmail()
                ))
                .collect(Collectors.toList());

        return new Result(collect);
    }
    /**
     * 회원 삭제
     */
    @DeleteMapping("/api/member/delete/{id}")
    public void deleteMember(@PathVariable("id") String id,@RequestBody DeleteMemberRequest request){
        Member member = DeleteMemberRequest.toEntity(request);
        memberService.deleteMember(member);
    }

}

