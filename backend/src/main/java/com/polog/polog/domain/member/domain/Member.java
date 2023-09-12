package com.polog.polog.domain.member.domain;


import com.polog.polog.domain.member.dto.MemberDto;
import com.polog.polog.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "email", length = 20, nullable = false)
    private String email;

    @OneToMany(mappedBy = "member")
    //@JsonBackReference //순환참조 방지
    private List<Post> posts = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    private Authority authority;


    private boolean enabled;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(authority.toString()));
//        // 다른 권한도 추가할 수 있으면 authorities 리스트에 추가합니다.
//        return authorities;
//    }
//
//    @Override
//    public String getUsername() {
//        return id;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return enabled;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return enabled;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return enabled;
//    }
//


    public void setPassword(String password) { this.password = password; }

//    @Builder
//    public Member(String id, String password, String email) {
//        this.id = id;
//        this.password = password;
//        this.email = email;
//    }


    // === 생성 메서드 ===
    public static MemberDto toDto(Member member){
        return MemberDto.builder()
                .uid(member.getUid())
                .id(member.getId())
                .password(member.getPassword())
                .email(member.getEmail())
                .build();
    }


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
