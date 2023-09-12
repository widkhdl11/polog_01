package com.polog.polog.domain.category.dto;

import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.member.dto.MemberDto;
import com.polog.polog.domain.post.dto.UpdatePostRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Long uid;
    private String name;
    private Long parentUid;
    private int order;
    private MemberDto member;
    private int step;

    
    //=== 비즈니스 로직 ===

    /**
     * CategoryDto를 Category Entity로 변환
     * @param request
     * @return
     */
    public static Category ToEntity(CategoryDto request){
        return Category.builder()
                .uid(request.getUid())
                .name(request.getName())
                .parentUid(request.getParentUid())
                .order(request.getOrder())
                .member(MemberDto.toEntity(request.getMember()))
                .step(request.getStep())
                .build();
    }

}
