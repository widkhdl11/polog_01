package com.polog.polog.domain.category.dto;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class CategoryMap {

    Map<CategoryDto, List<CategoryMap>> categoryMap = new HashMap<>();

    public void setCategoryMap(CategoryDto parentCategory, List<CategoryMap> childCategoryList) {
        categoryMap.put(parentCategory,childCategoryList);
    }
}
