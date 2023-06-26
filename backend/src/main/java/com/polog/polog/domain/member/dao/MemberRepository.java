package com.polog.polog.domain.member.dao;

import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.post.domain.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long uid) {
        return em.find(Member.class, uid);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findById(String id) {
        return em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void deleteByMember(Member member){
        em.remove(member);
    }

    // 회원 삭제를 위한 관련 포스트 삭제
    public List<Post> findIncludeCategory(Long memberUid){
        return em.createQuery("select p from Post p join fetch p.member m where m.uid = :memberUid", Post.class)
                .setParameter("memberUid", memberUid)
                .getResultList();
    }

    // 회원 삭제를 위한 연관 포스트 삭제
    public void deletePost(Post post){
        em.remove(post);
    }
    // ---------------------------

}
