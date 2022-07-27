package com.example.board.web.repository;

import com.example.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    /*
        N:1 fetch join query
        return entity-graph consists of post(N) with Member(1)
    */
    @Query("select p from Post p join fetch p.member order by p.id")
    List<Post> findAllWithMemberUsingFetchJoin();

    /*
        N+1 query:
     */
    @Query("select p from Post p order by p.id")
    List<Post> findAll();




}
