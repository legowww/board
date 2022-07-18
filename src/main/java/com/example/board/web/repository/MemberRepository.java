package com.example.board.web.repository;

import com.example.board.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //==해당하는 결과가 없다면 자동으로 null 반환==
    Member findMemberByLoginId(@Param("loginId") String loginId);
    Member findMemberByLoginIdAndPassword(@Param("loginId") String loginId,
                                          @Param("password") String password);
}
