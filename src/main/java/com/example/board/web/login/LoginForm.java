package com.example.board.web.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
public class LoginForm {
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
}
