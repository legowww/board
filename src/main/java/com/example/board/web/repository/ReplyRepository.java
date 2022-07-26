package com.example.board.web.repository;

import com.example.board.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    //==게시글 댓글 조회 쿼리(N + 1)==
    @Query("select r from Reply r join r.post p where p.id = :post_id order by r.createDate")
    List<Reply> findReplyByPostId(@Param("post_id") Long id);

    //==댓글이 달린 게시물의 id를 반환하는 쿼리==
    @Query("select r.post.id from Reply r where r.id = :reply_id")
    String findPostIdByReply(@Param("reply_id") Long id);
}

