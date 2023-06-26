package com.polog.polog.global.util;

import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.post.domain.Post;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

        public void dbInit1() {
            System.out.println("Init1" + this.getClass());
            Member member1 = createMember("userA", "1234", "abc@naver.com");
            em.persist(member1);

            Category category1 = createCategory("Cate1", 0);
            Category category2 = createCategory("Cate2", 1, 1L);

            em.persist(category1);
            em.persist(category2);

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

        private Member createMember(String id, String password, String email) {
            Member member = Member.builder()
                    .id(id)
                    .password(password)
                    .email(email)
                    .build();
            return member;
        }

        private Category createCategory(String name, int order, Long parentUid) {
            return Category.builder()
                    .name(name)
                    .order(order)
                    .parentUid(parentUid)
                    .build();
        }
        private Category createCategory(String name, int order) {
            return Category.builder()
                    .name(name)
                    .order(order)
                    .build();
        }


    }
}

