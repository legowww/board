package com.example.board.web.repository;

import com.example.board.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //[회원가입] 중복 아이디 검증
    Member findMemberByLoginId(@Param("loginId") String loginId);

    //[로그인] 아이디&&비번 일치 여부 검증
    Member findMemberByLoginIdAndPassword(@Param("loginId") String loginId,
                                          @Param("password") String password);

    //[Pageable 테스트] Page 반환결과 알아보기
    Page<Member> findByNameContaining(String name, Pageable pageable);
}
