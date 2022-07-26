package com.example.board.web.service;

import com.example.board.domain.Reply;
import com.example.board.web.dto.reply.ReplyPrintDto;
import com.example.board.web.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;

    public List<ReplyPrintDto> findPostsReplies(Long id) {
        List<Reply> result = replyRepository.findReplyByPostId(id);
        List<ReplyPrintDto> collect = result.stream()
                .map(reply -> new ReplyPrintDto(reply))
                .collect(Collectors.toList());
        return collect;
    }
}
