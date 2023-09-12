package com.polog.polog.domain.category.application;

import com.polog.polog.domain.category.dao.CategoryRepository;
import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.post.domain.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final EntityManager em;

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
    public List<Category> findAllCategory(String memberId){
        return categoryRepository.findAll(memberId);
    }

    /**
     * parentUid = 0L 인 카테고리 찾기
     */
    public List<Category> findNonParentUid(String memberId){
        return categoryRepository.findNonParentUid(memberId);
    }

    /**
     * 해당 parentUid를 가진 카테고리
     */
    public List<Category> findParentUid(String memberId,Long parentUid){
        return categoryRepository.findParentUid(memberId,parentUid);
    }


    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteCategory(Category category){

        // 카테고리 삭제를 위한 연관 포스트 삭제
        Category findCategory = categoryRepository.findOne(category.getUid());

        List<Post> findPostList = categoryRepository.findIncludeCategory(category.getUid(), category.getMember().getUid());

        for(Post post : findPostList){
            categoryRepository.deletePost(post);
        }

        if(findCategory != null){
            categoryRepository.delete(findCategory);
        }
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
