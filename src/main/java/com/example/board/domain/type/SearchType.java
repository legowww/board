package com.example.board.domain.type;

import lombok.Getter;

@Getter
public enum SearchType {
    TITLE("제목"),
    CONTENT("본문"),
    WRITER("작성자");
    private final String info;
    SearchType(String info) {
        this.info = info;
    }
}
