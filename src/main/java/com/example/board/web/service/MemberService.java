package com.example.board.web.service;

import com.example.board.domain.member.Member;
import com.example.board.domain.member.MemberStatus;
import com.example.board.web.dto.member.MemberSaveDto;
import com.example.board.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public boolean save(MemberSaveDto requestDto) {
        /*
            duplicate loginId check
         */
        Member checkMember = memberRepository.findMemberByLoginId(requestDto.getLoginId());
        if (checkMember == null) {
            memberRepository.save(requestDto.toEntity(MemberStatus.USER));
            return true;
        }
        return false;
    }
}
