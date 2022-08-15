package com.example.board.web.repository;

import com.example.board.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PostRepository extends JpaRepository<Post, Long> {
    /*
        N:1 fetch join query
        return entity-graph consists of post(N) with Member(1)

        (fetch join + paging) *countQuery*
    */
    @Query(
            value = "select p from Post p join fetch p.member",
            countQuery = "select count(p) from Post p join p.member")
    Page<Post> findAllWithMemberUsingFetchJoin(Pageable pageable);





}
