package com.polog.polog.domain.category.dto;

import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateCategoryRequest {

    private String name;
    private Long parentUid;
    private int order;
    private int step;
    private MemberDto member;

    public static Category toEntity(CreateCategoryRequest request){
        return Category.builder()
                .name(request.getName())
                .parentUid(request.getParentUid())
                .order(request.getOrder())
                .step(request.getStep())
                .member(MemberDto.toEntity(request.member))
                .build();
    }
}
