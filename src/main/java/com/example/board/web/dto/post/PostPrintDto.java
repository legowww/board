package com.example.board.web.dto.post;

import com.example.board.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;

@Slf4j
@Getter @Setter
@NoArgsConstructor
public class PostPrintDto {
    private Long postId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private Long WriterId;
    private String writerName;

    //find post in repository --> DTO
    public PostPrintDto(Post post) {
        postId = post.getId();
        title = post.getTitle();
        content = post.getContent();
        WriterId = post.getMember().getId();  //todo: LAZY loading check -> getId()는 이미 알고있으므로 이 때 호출 X
        writerName = post.getMember().getName();
    }
}
