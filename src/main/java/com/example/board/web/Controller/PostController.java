package com.example.board.web.Controller;

import com.example.board.domain.member.Member;
import com.example.board.web.dto.post.PostPrintDto;
import com.example.board.web.dto.post.PostSaveDto;
import com.example.board.web.dto.reply.ReplyPrintDto;
import com.example.board.web.dto.reply.ReplySaveDto;
import com.example.board.web.login.SessionName;
import com.example.board.web.service.PostService;
import com.example.board.web.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final ReplyService replyService;

    @GetMapping("postId={id}")
    public String post(@PathVariable Long id, Model model,
                       @SessionAttribute(name = SessionName.SESSION_LOGIN, required = false) Member loginMember,
                       HttpServletRequest request) {
        PostPrintDto post = postService.findById(id);
        List<ReplyPrintDto> replies = replyService.findPostsReplies(id);

        model.addAttribute("post", post);
        model.addAttribute("replies", replies);

        if (loginMember == null) {
            String requestURI = request.getRequestURI();
            model.addAttribute("currPageURI", requestURI);
            return "post/post";
        } else {
            if (loginMember.getId() == post.getWriterId()) {
                model.addAttribute("myPost", true);
            }
            model.addAttribute("reply", new ReplySaveDto());
            return "post/loginPost";
        }
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "post/index";
    }

    @GetMapping("/save")
    public String postsCreateForm(@ModelAttribute("post") PostSaveDto postSaveDto) {
        return "post/postSaveForm";
    }

    @PostMapping("/save")
    public String postsSave(@Validated @ModelAttribute("post") PostSaveDto postSaveDto,
                            BindingResult bindingResult,
                            @SessionAttribute(name = SessionName.SESSION_LOGIN, required = false) Member loginMember) {
        if (bindingResult.hasErrors()) {
            return "post/postSaveForm";
        }
        Long writerId = loginMember.getId();
        log.info("id=" + writerId);
        postService.save(writerId, postSaveDto);
        return "redirect:/post/index";
    }

    @GetMapping("/update/postId={postId}")
    public String postUpdateForm(@PathVariable Long postId, Model model) {
        PostPrintDto findPost = postService.findById(postId);
        model.addAttribute("post", findPost);
        return "/post/postUpdateForm";
    }

    @PostMapping("/update/postId={postId}")
    public String postsUpdate(@PathVariable Long postId, @Validated @ModelAttribute("post") PostPrintDto postPrintDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("errors = " + bindingResult);
            return "/post/postUpdateForm";
        }
        postService.update(postId, postPrintDto.getTitle(), postPrintDto.getContent());
        return "redirect:/post/postId=" + postId;
    }

    @GetMapping("/delete/postId={postId}")
    public String postDelete(@PathVariable Long postId) {
        postService.delete(postId);
        return "redirect:/post/index";
    }
}
