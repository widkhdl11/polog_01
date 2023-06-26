package com.polog.polog.domain.category.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCategoryListResponse {

    private Long uid;

    private String name;
    private Long parentUid;
    private int order;

}
