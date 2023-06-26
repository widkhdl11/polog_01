package com.polog.polog.domain.category.api;


import com.polog.polog.domain.category.application.CategoryService;
import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.category.dto.*;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.member.dto.MemberDto;
import com.polog.polog.domain.post.application.PostService;
import com.polog.polog.global.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 생성
     * @param request
     * @return
     */
    @PostMapping("/api/category/create")
    public CreateCategoryResponse createCategory(@RequestBody CreateCategoryRequest request){

        Long createUid = categoryService.createCategory(CreateCategoryRequest.toEntity(request));

        List<Category> categoryList = categoryService.findAllCategory();

        List<CreateCategoryListResponse> collect = categoryList.stream()
                .map(c -> new CreateCategoryListResponse(
                        c.getUid(),
                        c.getName(),
                        c.getParentUid(),
                        c.getOrder()
                ))
                .collect(Collectors.toList());
        return new CreateCategoryResponse(createUid,collect);
    }

    /**
     * 카테고리 목록
     * @return
     */
    @PostMapping("/api/categorys/")
    public Result<CreateCategoryListResponse> findAllCategory(){

        List<Category> categoryList = categoryService.findAllCategory();
        List<CreateCategoryListResponse> collect = categoryList.stream()
                .map(c -> new CreateCategoryListResponse(
                        c.getUid(),
                        c.getName(),
                        c.getParentUid(),
                        c.getOrder()
                ))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    /**
     * 카테고리 수정
     * @param request
     */
    @PutMapping("/api/category/update/{name}")
    public void updateCategory(@PathVariable("name") String name, @RequestBody UpdateCategoryRequest request){

        categoryService.updateCategory(UpdateCategoryRequest.toEntity(request));

    }

    /**
     * 카테고리 삭제
     * @param request
     */
    @DeleteMapping("/api/category/delete/{name}")
    public void deleteCategory(@PathVariable("name") String name, @RequestBody DeleteCategoryRequest request){

        Category c = DeleteCategoryRequest.toEntity(request);
        Member m = MemberDto.toEntity(request.getMember());
        categoryService.deleteCategory(m, c);
    }
}
