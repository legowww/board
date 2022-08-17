package com.example.board.web.dto.post;

import com.example.board.domain.Post;
import com.example.board.domain.type.PostType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class PostSaveDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private PostType type;
}
