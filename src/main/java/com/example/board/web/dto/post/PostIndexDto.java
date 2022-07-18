package com.example.board.web.dto.post;

import com.example.board.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class PostIndexDto {
    private Long id;
    private String title;
    private String writerName;
    private LocalDateTime createdDate;

    public PostIndexDto(Post p) {
        id = p.getId();
        title = p.getTitle();
        writerName = p.getMember().getName(); // todo: fetch join apply check
        createdDate = p.getCreateDate();
    }
}
