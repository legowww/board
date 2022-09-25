package com.example.board.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@EqualsAndHashCode //equals&hashcode 필수
@NoArgsConstructor //기본생성자 필수
public class LikeId implements Serializable {
    private Long post;
    private Long member;
}
