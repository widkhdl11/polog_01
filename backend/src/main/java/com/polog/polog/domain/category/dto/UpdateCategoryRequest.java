package com.polog.polog.domain.category.dto;

import com.polog.polog.domain.category.domain.Category;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class UpdateCategoryRequest {

    private Long uid;

    private String name;

    private Long parentUid;

    private int order;
    private int step;


    //=== 비즈니스 로직 ===
    public static Category toEntity(UpdateCategoryRequest request) {
        return Category.builder()
                .uid(request.getUid())
                .name(request.getName())
                .parentUid(request.getParentUid())
                .order(request.getOrder())
                .step(request.getStep())
                .build();

    }

}
