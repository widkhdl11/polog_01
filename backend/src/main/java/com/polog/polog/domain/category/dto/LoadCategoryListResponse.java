package com.polog.polog.domain.category.dto;


import com.polog.polog.domain.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoadCategoryListResponse {

    private Category parentCategory;
    private List<Category> categoryList = new ArrayList<>();


}