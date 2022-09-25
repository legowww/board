package com.example.board.web.repository;

import com.example.board.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    boolean existsByPost_IdAndMember_Id(Long postId, Long memberId);
    Bookmark findByPost_IdAndMember_Id(Long postId, Long memberId);
}
