package com.example.board.web.service;

import com.example.board.domain.Post;
import com.example.board.domain.Reply;
import com.example.board.domain.member.Member;
import com.example.board.web.dto.reply.ReplyPrintDto;
import com.example.board.web.repository.MemberRepository;
import com.example.board.web.repository.PostRepository;
import com.example.board.web.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public List<ReplyPrintDto> findPostsReplies(Long id) {
        List<Reply> result = replyRepository.findReplyByPostIdUsingFetchJoin(id);
        List<ReplyPrintDto> collect = result.stream()
                .map(reply -> new ReplyPrintDto(reply))
                .collect(Collectors.toList());
        return collect;
    }

    @Transactional
    public void replyCreate(Long postId, String writerLoginId, String content) {
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("no exist Id = " + postId));
        Member findMember = memberRepository.findMemberByLoginId(writerLoginId);
        Reply reply = Reply.createReply(findMember, findPost, content);
        replyRepository.save(reply);
    }
}
