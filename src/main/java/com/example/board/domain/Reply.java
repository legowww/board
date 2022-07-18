package com.example.board.domain;

import com.example.board.domain.member.Member;
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

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * reply on posts logic
     */
    public static Reply createReply(Member member, Post post, String content) {
        Reply reply = new Reply(content);
        reply.WrittenBy(member);
        reply.WrittenOn(post);
        return reply;
    }

    private void WrittenOn(Post post) {
        this.post = post;
        post.getReplies().add(this);
    }

    public void WrittenBy(Member member) {
        this.member = member;
        member.getReplies().add(this);
    }

    public Reply(String content) {
        this.content = content;
    }
}