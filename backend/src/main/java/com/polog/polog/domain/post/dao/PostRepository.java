package com.polog.polog.domain.post.dao;

import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.post.domain.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
// 생성자 Autowired를 위해 @AllArgsConstructor를 쓰다가
// 그보다 더 나은 final이 붙은 것만 생성자를 생성해주는
// @RequiredArgsConstructor 사용
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public Post findOne(long uid){
        return em.find(Post.class, uid);
    }

    public List<Post> findAllByMember(Long uid){
        return em.createQuery("select p from Post p join fetch p.member m where m.uid = :uid", Post.class)
                .setParameter("uid", uid)
                .getResultList();
    }



    public void delete(Post post){
        em.remove(post);
    }

}
