package com.polog.polog.domain.member.dao;

import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.post.domain.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }

    public Member findOne(Long uid) {
        return em.find(Member.class, uid);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Member findById(String id) {
        return em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Optional<Member> opFindByOne(Long uid) {
        return Optional.ofNullable(em.createQuery("select m from Member m where m.uid = :uid", Member.class)
                .setParameter("uid", uid)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null));
    }

    public Optional<Member> opFindById(String id) {
        return Optional.ofNullable(em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null));

    }



    public void deleteByMember(Optional<Member> member){
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
