package com.polog.polog.domain.member.domain;


import com.polog.polog.domain.member.dto.UpdateMemberRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
//@Builder
@Table(name="members_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 기본 생성자를 생성하는 어노테이션입니다.
// 기본 생성자에 접근 제어자를 설정하지 않으면
// 무분별하게 객체가 생성될 수 있기 때문에
// access 속성을 통해 설정합니다.
 @AllArgsConstructor(access = AccessLevel.PRIVATE)
// 모든 멤버변수를 가지고 있는 생성자를 생성합니다.
@EqualsAndHashCode
@Builder
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_uid")
    private Long uid;

    @Column(name = "id", length = 10, nullable = false)
    private String id;

    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @Column(name = "email", length = 20, nullable = false)
    private String email;

//    @Builder
//    public Member(String id, String password, String email) {
//        this.id = id;
//        this.password = password;
//        this.email = email;
//    }

    // === 비즈니스 로직 ===

    /**
     * 회원 정보 변경
     */
    public void changeMember(Member member){
        this.id = member.getId();
        this.password = member.getPassword();
        this.email = member.getEmail();
    }


}
