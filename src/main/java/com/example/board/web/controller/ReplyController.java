package com.example.board.web.controller;

import com.example.board.web.dto.reply.ReplySaveDto;
import com.example.board.web.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/replies")
@Slf4j
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/{postId}")
    public String createReply(@PathVariable Long postId, @ModelAttribute ReplySaveDto replySaveDto) {
        replyService.createReply(postId, replySaveDto.getWriterLoginId(), replySaveDto.getContent());
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/delete/{postId}/{replyId}")
    public String deleteReply(@PathVariable Long postId, @PathVariable Long replyId) {
        replyService.deleteReply(replyId);
        return "redirect:/posts/" + postId;
    }
}
