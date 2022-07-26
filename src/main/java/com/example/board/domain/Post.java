package com.example.board.domain;

import com.example.board.domain.member.Member;
import jdk.dynalink.linker.LinkerServices;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    //todo: cascade 를 통한 제거는 mysql 에서는 불가능하다 -> 테이블 생성시 외래키 무결성 조건을 추가해야 한다.
    //게시물 제거 시 댓글 모두 제거 -> member 제거 post 제거를 통해 reply 제거 현황을 보고 방법 결정
    //1.orphan
    //2.casade.REMOVE
    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();


    public static Post createPost(Member member, String title, String content) {
        Post post = new Post(title, content);
        post.writtenBy(member);
        return post;
    }

    private void writtenBy(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    //==업데이트==
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
