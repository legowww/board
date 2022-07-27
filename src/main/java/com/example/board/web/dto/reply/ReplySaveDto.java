package com.example.board.web.dto.reply;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ReplySaveDto {
    private String writerLoginId;
    private String writerName;
    private String content;

    public ReplySaveDto(String writerLoginId, String writerName) {
        this.writerLoginId = writerLoginId;
        this.writerName = writerName;
    }
}
