package com.example.board.domain.type;


import lombok.Getter;

@Getter
public enum PostType {
    STUDY("공부"), // name(info)
    MARKET("장터"),
    FASHION("패션");
    private final String info;
    PostType(String info) {
        this.info = info;
    }
}
