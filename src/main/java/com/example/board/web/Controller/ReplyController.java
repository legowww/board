package com.example.board.web.Controller;

import com.example.board.web.dto.reply.ReplySaveDto;
import com.example.board.web.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
@Slf4j
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/postId={postId}")
    public String replyCreate(@PathVariable Long postId, @ModelAttribute ReplySaveDto replySaveDto) {
        replyService.replyCreate(postId, replySaveDto.getWriterLoginId(), replySaveDto.getContent());
        return "redirect:/post/postId=" + postId;
    }
}
