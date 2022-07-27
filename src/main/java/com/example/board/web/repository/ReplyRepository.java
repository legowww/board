package com.example.board.web.repository;

import com.example.board.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    //N+1 문제 해결 쿼리
    @Query("select r from Reply r join fetch r.member where r.post.id = :post_id order by r.createDate")
    List<Reply> findReplyByPostIdUsingFetchJoin(@Param("post_id") Long id);

    //==게시글 댓글 조회 쿼리(N + 1)==
    @Query("select r from Reply r join r.post p where p.id = :post_id order by r.createDate")
    List<Reply> findReplyByPostId(@Param("post_id") Long id);
}

