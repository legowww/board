package com.example.board.web.Controller;

import com.example.board.domain.member.Member;
import com.example.board.web.dto.post.PostSaveDto;
import com.example.board.web.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "post/index";
    }

    @GetMapping("/save")
    public String postsCreateForm(@ModelAttribute("post") PostSaveDto postSaveDto) {
        return "post/postsSaveForm";
    }

    @PostMapping("/save")
    public String postsSave(@Validated @ModelAttribute("post") PostSaveDto postSaveDto,
                            BindingResult bindingResult,
                            @SessionAttribute(name = "SESSION_MEMBER", required = false) Member loginMember) {
        if (bindingResult.hasErrors()) {
            return "post/postsSaveForm";
        }

        Long writerId = loginMember.getId();
        log.info("id=" + writerId);
        postService.save(writerId, postSaveDto);
        return "redirect:/post/index";
    }
}
