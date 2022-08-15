package com.example.board.web.dto.member;

import com.example.board.domain.Member;
import com.example.board.domain.MemberStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
public class MemberSaveDto {
    @NotBlank
    private String name;
    @NotNull
    private Integer age;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;

    public Member toEntity(MemberStatus memberStatus) {
        return Member.builder()
                .age(age)
                .name(name)
                .loginId(loginId)
                .password(password)
                .memberStatus(memberStatus)
                .build();
    }
}
