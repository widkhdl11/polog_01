package com.polog.polog.domain.category.dto;


import com.polog.polog.domain.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoadCategoryMapResponse {

    Map<Category, List<LoadCategoryMapResponse>> categoryMap = new HashMap<>();

    // categoryMap 설정을 위한 메서드
    public void setCategoryMap(Category category, List<LoadCategoryMapResponse> child) {
        categoryMap.put(category, child);
    }

    // categoryMap 반환을 위한 메서드
    public Map<Category, List<LoadCategoryMapResponse>> getCategoryMap() {
        return categoryMap;
    }
}
