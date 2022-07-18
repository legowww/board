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

    @OneToMany(mappedBy = "post")
    private List<Reply> replies = new ArrayList<>();

    //==업데이트==
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Post createPost(Member member, String title, String content) {
        Post post = new Post(title, content);
        post.writtenBy(member);
        return post;
    }

    private void writtenBy(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }


    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
