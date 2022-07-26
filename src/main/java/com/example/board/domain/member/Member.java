package com.example.board.domain.member;

import com.example.board.domain.BaseTimeEntity;
import com.example.board.domain.Post;
import com.example.board.domain.Reply;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "loginId")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    //==양방향 관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    //==양방향 관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Reply> replies = new ArrayList<>();

    @Builder
    public Member(String name, Integer age, String loginId, String password, MemberStatus memberStatus) {
        this.name = name;
        this.age = age;
        this.loginId = loginId;
        this.password = password;
        this.memberStatus = memberStatus;
    }
}
