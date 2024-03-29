package com.example.board.web.dto.post;

import com.example.board.domain.Post;
import com.example.board.domain.type.PostType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;

@Slf4j
@Getter @Setter
@NoArgsConstructor
public class PostDto {
    private Long postId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private Long WriterId;
    private String writerLoginId;
    private String writerName;
    private String createdDate;
    private int repliesSize;
    private String clickTitleName;
    private int viewCount;
    private int like;
    private PostType type;
    private void setClickTitleName( ) {
        if (repliesSize == 0)
            clickTitleName = title;
        else {
            clickTitleName = title + " " + "[" + repliesSize + "]";
        }
    }

    public PostDto(Post post) {
        postId = post.getId();
        title = post.getTitle();
        content = post.getContent();
        WriterId = post.getMember().getId();
        writerLoginId = post.getMember().getLoginId();  //todo: fetch join apply check
        log.info("==============2-1단계==============");
        writerName = post.getMember().getName();
        createdDate =  post.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm"));
        repliesSize = post.getReplies().size();  //todo: batch size apply check, 한 건 조회면 in 사용하지 않음
        log.info("==============2-2단계==============");
        viewCount = post.getViewCount();
        setClickTitleName();
        like = post.getLikes().size(); //todo: batch size apply check, 한 건 조회면 in 사용하지 않음
        log.info("==============2-3단계==============");
        type = post.getType();
    }
}
