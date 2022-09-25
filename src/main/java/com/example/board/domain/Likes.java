package com.example.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(LikeId.class)
@Entity
public class Likes {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    public Post post; //식별자 클래스는 public

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    public Member member;

    private Likes(Post post, Member member) {
        this.post = post;
        this.member = member;
    }

    public static Likes createLike(Post post, Member member) {
        return new Likes(post, member);
    }
}
