package com.example.board.domain;

import com.example.board.domain.type.PostType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PostType type;

    //todo: cascade 를 통한 제거는 mysql 에서는 불가능하다 -> 테이블 생성시 외래키 무결성 조건을 추가해야 한다.
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Reply> replies = new ArrayList<>();

    //추천수 있는 게시물 삭제하기 위해 부모 테이블인 Post 에 cascade 추가.
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Likes> likes = new ArrayList<>();

    @Column(name = "likes")
    private int likeCount;

    @Column(name = "view")
    private int viewCount;

    private Post(String title, String content, PostType type) {
        this.title = title;
        this.content = content;
        this.type = type;
    }

    public static Post createPost(Member member, String title, String content, PostType type) {
        Post post = new Post(title, content, type);
        post.writtenBy(member);
        return post;
    }

    private void writtenBy(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    //==게시글 업데이트==
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //==조회수 증가==
    public void updateView() {
        this.viewCount += 1;
    }

    //==추천수 증가==
    public void updateLikes() {
        this.likeCount += 1;
    }
}
