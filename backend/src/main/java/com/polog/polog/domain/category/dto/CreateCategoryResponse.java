package com.polog.polog.domain.category.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class CreateCategoryResponse {


    private Long uid;

    private List<CreateCategoryListResponse> categoryList = new ArrayList<>();
}
