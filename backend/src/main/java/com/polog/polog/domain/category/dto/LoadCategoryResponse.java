package com.polog.polog.domain.category.dto;


import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class LoadCategoryResponse {

    private Long uid;

    private String name;
    private Long parentUid;
    private int order;
    private int step;

    public static LoadCategoryResponse toDto(Category category){
        return LoadCategoryResponse.builder()
                .uid(category.getUid())
                .name(category.getName())
                .parentUid(category.getParentUid())
                .order(category.getOrder())
                .step(category.getStep())
                .build();
    }

}
