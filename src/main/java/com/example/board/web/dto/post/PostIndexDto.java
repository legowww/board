package com.example.board.web.dto.post;

import com.example.board.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@NoArgsConstructor
public class PostIndexDto {
    private Long postId;
    private String title;
    private Long writerId;
    private String writerName;
    private String createdDate;

    public PostIndexDto(Post p) {
        postId = p.getId();
        title = p.getTitle();
        writerId = p.getMember().getId();   //todo: fetch join apply check
        writerName = p.getMember().getName();
        createdDate =  p.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm"));
    }
}
