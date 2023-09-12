package com.polog.polog.domain.category.dto;


import com.polog.polog.domain.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParentUidMap {

    Map<Long, List<CategoryDto>> parentUidMap = new HashMap<>();

    public void setParentUidMap(Long parentUid, List<CategoryDto> targetCategoryList) {
        parentUidMap.put(parentUid,targetCategoryList);
    }
}
