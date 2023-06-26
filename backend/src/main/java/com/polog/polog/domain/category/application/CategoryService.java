package com.polog.polog.domain.category.application;

import com.polog.polog.domain.category.dao.CategoryRepository;
import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.category.dto.UpdateCategoryRequest;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.post.application.PostService;
import com.polog.polog.domain.post.dao.PostRepository;
import com.polog.polog.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 생성
     * @param category
     * @return
     */
    @Transactional
    public Long createCategory(Category category){

        categoryRepository.save(category);

        return category.getUid();
    }

    /**
     * 카테고리 하나 찾기
     */
    public Category findOneCategory(Long uid){
        return categoryRepository.findOne(uid);
    }

    /**
     * 카테고리 모두 찾기
     */
    public List<Category> findAllCategory(){
        return categoryRepository.findAll();
    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteCategory(Member member, Category category){

        // 카테고리 삭제를 위한 연관 포스트 삭제
        List<Post> findPostList = categoryRepository.findIncludeCategory(member.getUid(), category.getUid());
        for(Post post : findPostList){
            categoryRepository.deletePost(post);
        }


        categoryRepository.delete(category);
    }

    /**
     * 카테고리 수정
     */
    @Transactional
    public void updateCategory(Category category){
        Category findCategory = categoryRepository.findOne(category.getUid());

        findCategory.updateCategory(category);
        categoryRepository.save(findCategory);
    }


}
