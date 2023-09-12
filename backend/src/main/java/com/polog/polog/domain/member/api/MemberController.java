package com.polog.polog.domain.member.api;


import com.polog.polog.domain.member.application.MemberService;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.member.dto.*;
import com.polog.polog.global.auth.service.AuthService;
import com.polog.polog.global.dto.token.TokenDto;
import com.polog.polog.global.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

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
        memberService.deleteMember(member.getUid());
    }

    /**
     * 회원 찾기
     */
    @GetMapping("/api/member/{memberId}")
    public Member findOneMemberId(@PathVariable("memberId") String memberId){
        Member member = memberService.findOneMemberId(memberId);
        return member;
    }


    @PostMapping("/api/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @PostMapping("/api/login")
    public ResponseEntity<TokenDto> login(HttpServletRequest request,@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(request,requestDto));
    }


//    @PatchMapping("/logout")
//    public ResponseEntity logout(TokenDto request) {
//        String encryptedRefreshToken = jwtTokenProvider.resolveRefreshToken(request);
//        String accessToken = jwtTokenProvider.resolveAccessToken(request);
//        authService.logout(encryptedRefreshToken, accessToken);
//
//        return new ResponseEntity<>(new SingleResponseDto<>("Logged out successfully"), HttpStatus.NO_CONTENT);
//    }
}

