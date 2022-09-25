package com.example.board.web.repository;

import com.example.board.domain.LikeId;
import com.example.board.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, LikeId> {
    Long countByPost_Id(Long postId);
    boolean existsByPost_IdAndMember_Id(Long postId, Long memberId);
}
