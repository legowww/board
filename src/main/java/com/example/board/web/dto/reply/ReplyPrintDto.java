package com.example.board.web.dto.reply;

import com.example.board.domain.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyPrintDto {
    private Long id;
    private String memberLoginId;
    private String memberName;
    private String content;
    private String createDate;

    public ReplyPrintDto(Reply reply) {
        id = reply.getId();
        memberLoginId = reply.getMember().getLoginId();
        memberName = reply.getMember().getName();
        content = reply.getContent();
        createDate =  reply.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm"));
    }
}