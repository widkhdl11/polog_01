package com.polog.polog.domain.category.dto;

import com.polog.polog.domain.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateCategoryRequest {

    private String name;
    private Long parentUid;
    private int order;

    public static Category toEntity(CreateCategoryRequest request){
        return Category.builder()
                .name(request.getName())
                .parentUid(request.getParentUid())
                .order(request.getOrder())
                .build();
    }
}
