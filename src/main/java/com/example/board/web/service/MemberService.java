package com.example.board.web.service;

import com.example.board.domain.Member;
import com.example.board.domain.MemberStatus;
import com.example.board.web.dto.member.MemberInfoDto;
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

    //==중복 loginId 체크==
    @Transactional
    public boolean save(MemberSaveDto requestDto) {
        Member checkMember = memberRepository.findMemberByLoginId(requestDto.getLoginId());
        if (checkMember == null) {
            memberRepository.save(requestDto.toEntity(MemberStatus.USER));
            return true;
        }
        return false;
    }

    public MemberInfoDto findMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("no exist Id = " + memberId));
        return new MemberInfoDto(member);
    }

}
