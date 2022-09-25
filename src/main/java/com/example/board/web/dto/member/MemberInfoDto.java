package com.example.board.web.dto.member;


import com.example.board.domain.Member;
import com.example.board.domain.MemberStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberInfoDto {
    private String loginId;
    private Integer age;
    private String name;
    private MemberStatus memberStatus;

    public MemberInfoDto(Member member) {
        this.name = member.getName();
        this.loginId = member.getLoginId();
        this.age = member.getAge();
        this.memberStatus = member.getMemberStatus();
    }
}
