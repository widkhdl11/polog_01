package com.polog.polog.domain.category.application;

import com.polog.polog.domain.category.api.CategoryController;
import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.category.dto.CreateCategoryRequest;
import com.polog.polog.domain.category.dto.UpdateCategoryRequest;
import com.polog.polog.domain.member.application.MemberService;
import com.polog.polog.domain.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {


    @Autowired
    CategoryService categoryService;
    @Autowired
    MemberService memberService;



    @Test
    @DisplayName("카테고리 생성 및 가져오기")
    void createCategory() throws Exception {
        //given
        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .name("생성 테스트 카테고리")
                .order(1)
                .parentUid(1L)
                .build();


        //when
        Long createUid = categoryService.createCategory(CreateCategoryRequest.toEntity(request));
        Category findCategory = categoryService.findOneCategory(createUid);

        //then
        assertEquals(createUid,3L);
        assertEquals(createUid,findCategory.getUid());
        assertEquals(findCategory.getName(),"생성 테스트 카테고리");
        assertEquals(findCategory.getOrder(),1);
        assertEquals(findCategory.getParentUid(),1L);



    }

    @Test
    @DisplayName("카테고리 수정")
    void updateCategory() throws Exception  {
        // given
        Category category1 = categoryService.findOneCategory(1L);
        UpdateCategoryRequest request = UpdateCategoryRequest.builder()
                .uid(1L)
                .name("updateCategory1")
                .order(2)
                .parentUid(1L)
                .build();


        // when
        categoryService.updateCategory(UpdateCategoryRequest.toEntity(request));
        Category result = categoryService.findOneCategory(request.getUid());

        // then
        assertEquals(category1.getUid(), result.getUid());
        assertEquals(result.getName(), "updateCategory1");

    }


    @Test
    @DisplayName("모든 카테고리 출력")
    void findAllCategory() throws Exception  {
        //given

        //when
        List<Category> categoryList = categoryService.findAllCategory();

        //then
        assertEquals(categoryList.size(), 2);
    }

    @Test
    @DisplayName("카테고리 삭제")
    void deleteCategory() {
        //given
        Category findCategory = categoryService.findOneCategory(1L);
        Member findMember = memberService.findOneMember(1L);
        //when
        categoryService.deleteCategory(findMember, findCategory);

        //then

        List<Category> categoryList = categoryService.findAllCategory();
        assertEquals(categoryList.size(), 1);
    }
}