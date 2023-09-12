package com.polog.polog.global.util;

import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.member.domain.Authority;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.post.domain.Post;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 종 주문 2개
 * * userA
 * 	 * JPA1 BOOK
 * 	 * JPA2 BOOK
 * * userB
 * 	 * SPRING1 BOOK
 * 	 * SPRING2 BOOK
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        //initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;
        public void dbInit1() {
            System.out.println("Init1" + this.getClass());

            Member member1 = createMember("userA", passwordEncoder.encode("1234"), "abc@naver.com","ROLE_USER");

            em.persist(member1);

            Category category1 = createCategory("Cate1", 1, 0L, member1,1);
            Category category10 = createCategory("Cate10", 1, 1L, member1,2);
            Category category11 = createCategory("Cate11", 1, 10L, member1,3);
            
            Category category2 = createCategory("Cate2", 3, 0L, member1,1);
            Category category3 = createCategory("Cate3", 1, 2L, member1,2);
            Category category4 = createCategory("Cate4", 2, 2L, member1,2);

            Category category5 = createCategory("Cate5", 2, 0L, member1,1);
            Category category6 = createCategory("Cate6", 1, 5L, member1,2);
            Category category8 = createCategory("Cate8", 1, 6L, member1,3);
            Category category9 = createCategory("Cate9", 2, 6L, member1,3);

            Category category7 = createCategory("Cate7", 2, 5L, member1,2);


            em.persist(category1);
            em.persist(category2);
            em.persist(category3);
            em.persist(category4);
            em.persist(category5);
            em.persist(category6);
            em.persist(category7);
            em.persist(category8);
            em.persist(category9);
            em.persist(category10);
            em.persist(category11);

//            Category category2 = createCategory("Cate2", 0);
//            em.persist(category2);

            em.flush();

            //Post post1 = Post.createPost(member1,category1,"첫글제목","첫글 내용");

            Post post1 = new Post("첫글제목", "첫글 내용", member1, category1);
            em.persist(post1);
        }

//        public void dbInit2() {
//            Member member = createMember("userB", "진주", "2", "2222");
//            em.persist(member);
//
//            Book book1 = createBook("SPRING1 BOOK", 20000, 200);
//            em.persist(book1);
//
//            Book book2 = createBook("SPRING2 BOOK", 40000, 300);
//            em.persist(book2);
//
//            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
//            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);
//
//            Delivery delivery = createDelivery(member);
//            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
//            em.persist(order);
//        }

        private Member createMember(String id, String password, String email,String authenrity) {
            Member member = Member.builder()
                    .id(id)
                    .password(password)
                    .email(email)
                    .authority(Authority.ROLE_USER)
                    .build();
            return member;
        }

        private Category createCategory(String name, int order, Long parentUid, Member member,int step) {
            return Category.builder()
                    .name(name)
                    .order(order)
                    .parentUid(parentUid)
                    .member(member)
                    .step(step)
                    .build();
        }
    }
}

