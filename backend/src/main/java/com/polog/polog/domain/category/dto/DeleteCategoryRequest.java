package com.polog.polog.domain.category.dto;

import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeleteCategoryRequest {

    private Long uid;
    private MemberDto member;

    public static Category toEntity(DeleteCategoryRequest request){
        return Category.builder()
                .uid(request.getUid())
                .build();
    }
}
