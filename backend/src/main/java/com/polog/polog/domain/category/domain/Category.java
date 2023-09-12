package com.polog.polog.domain.category.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.polog.polog.domain.category.dto.CategoryDto;
import com.polog.polog.domain.category.dto.LoadCategoryResponse;
import com.polog.polog.domain.category.dto.UpdateCategoryRequest;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.member.dto.MemberDto;
import com.polog.polog.domain.post.dto.UpdatePostRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name="category_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 기본 생성자를 생성하는 어노테이션입니다.
// 기본 생성자에 접근 제어자를 설정하지 않으면
// 무분별하게 객체가 생성될 수 있기 때문에
// access 속성을 통해 설정합니다.
@AllArgsConstructor(access = AccessLevel.PRIVATE)
// 모든 멤버변수를 가지고 있는 생성자를 생성합니다.
@EqualsAndHashCode
@Builder
public class Category {

    @Id @GeneratedValue
    @Column(name ="category_uid")
    private Long uid;

    @Column(name= "category_name", length = 10, nullable = false)
    private String name;

    @Column(name="parent_uid")
    private Long parentUid;

    //순서
    @Column(name="category_order", nullable = false)
    private int order;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JsonManagedReference // 순환참조 방지
    @JoinColumn(name = "member_uid")
    private Member member;

    @Column(name = "category_step")
    private int step;

    // === 생성 메서드 ===
    public static CategoryDto toDto(Category category){
        return CategoryDto.builder()
                .uid(category.getUid())
                .name(category.getName())
                .parentUid(category.getParentUid())
                .order(category.getOrder())
                .member(Member.toDto(category.getMember()))
                .step(category.getStep())
                .build();
    }

    public static LoadCategoryResponse toLoadResponseDto(Category category){
        return LoadCategoryResponse.builder()
                .uid(category.getUid())
                .name(category.getName())
                .parentUid(category.getParentUid())
                .order(category.getOrder())
                .step(category.getStep())
                .build();
    }

    // === 비즈니스 로직 ===
    /**
     * 카테고리 수정
     */

    public void updateCategory(Category category){
        this.name = category.getName();
        this.order = category.getOrder();
        this.parentUid = category.getParentUid();
        this.step = category.getStep();
    }

}
