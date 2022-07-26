package com.example.board.web.dto.reply;

import com.example.board.domain.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyPrintDto {
    private Long id;
    private String memberName;
    private String content;

    public ReplyPrintDto(Reply reply) {
        id = reply.getId();
        memberName = reply.getMember().getName(); //todo: fetch join 적용대상
        content = reply.getContent();
    }
}