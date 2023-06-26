package com.polog.polog.domain.category.dao;

import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.post.domain.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final EntityManager em;

    public void save(Category category){
        em.persist(category);
    }

    public Category findOne(Long uid){
        return em.find(Category.class, uid);
    }
    public List<Category> findAll(){
        return em.createQuery("select c from Category c", Category.class)
                .getResultList();
    }

    public void delete(Category category){
        em.remove(category);
    }

    // 카테고리 삭제를 위한 연관 포스트 가져오기
    public List<Post> findIncludeCategory(Long memberUid, Long categoryUid){
        return em.createQuery("select p from Post p join fetch p.member m join fetch p.category c where m.uid = :memberUid and c.uid = :categoryUid", Post.class)
                .setParameter("memberUid", memberUid)
                .setParameter("categoryUid", categoryUid)
                .getResultList();
    }

    // 카테고리 삭제를 위한 연관 포스트 삭제
    public void deletePost(Post post){
        em.remove(post);
    }
    // -------------------------------

}
