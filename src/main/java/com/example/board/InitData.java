package com.example.board;


import com.example.board.domain.*;
import com.example.board.domain.type.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitData {

    private final InitService initService;

    @PostConstruct
    public void init() {
        //왜 이런 방식을? -> @PostConstruct 가 먼저 실행되고 이후에 @Transactional AOP 가 적용되기 때문
        initService.initData();
    }

    @Component
    @RequiredArgsConstructor
    static class InitService {

        //가짜(프록시) 엔티티 메니저를 제공, 호출하면 현재 트랜잭션에 맞는 엔티티 매니저 찾아서 연결시킴
        private final EntityManager em;

        @Transactional
        public void initData() {
            Member member = Member.builder()
                    .age(30)
                    .name("test")
                    .loginId("abc")
                    .password("123")
                    .memberStatus(MemberStatus.ADMIN)
                    .build();
            em.persist(member);

            Post p = Post.createPost(member, "likesTest", "likes?", PostType.STUDY);




            Likes like = Likes.createLike(p, member);
            em.persist(like);



            for (int i = 0; i < 50; ++i) {
                Member newMember = Member.builder()
                        .age(i + 1)
                        .name("test" + i)
                        .loginId("abc" + i)
                        .password("123" + i)
                        .memberStatus(MemberStatus.ADMIN)
                        .build();
                em.persist(newMember);

                if (i >= 0 && i <= 12) {
                    Post post = Post.createPost(newMember, "title" + i, "content", PostType.FASHION);
                    Likes newLike = Likes.createLike(p, newMember);
                    em.persist(post);
                    em.persist(newLike);
                }
            }




            //using dirty checking
//            post.update("updatedTitle", "updatedContent");
//            Reply reply = Reply.createReply(member, post, "replyContent");
//            em.persist(reply);
        }
    }
}
