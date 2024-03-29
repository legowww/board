package com.example.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    private Reply(String content) {
        this.content = content;
    }

    public static Reply createReply(Member member, Post post, String content) {
        Reply reply = new Reply(content);
        reply.writtenBy(member);
        reply.writtenOn(post);
        return reply;
    }

    private void writtenOn(Post post) {
        this.post = post;
        post.getReplies().add(this);
    }

    public void writtenBy(Member member) {
        this.member = member;
        member.getReplies().add(this);
    }
}