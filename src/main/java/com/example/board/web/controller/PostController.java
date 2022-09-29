package com.example.board.web.controller;

import com.example.board.domain.Member;
import com.example.board.domain.type.PostType;
import com.example.board.domain.type.SearchType;
import com.example.board.web.dto.post.PostDto;
import com.example.board.web.dto.post.PostSaveDto;
import com.example.board.web.dto.reply.ReplyPrintDto;
import com.example.board.web.dto.reply.ReplySaveDto;
import com.example.board.web.login.SessionName;
import com.example.board.web.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final ReplyService replyService;
    private final PaginationService paginationService;
    private final LikesService likesService;
    private final BookmarkService bookmarkService;
    @GetMapping
    public String posts(@RequestParam(required = false) PostType postType,
                        @RequestParam(required = false) SearchType searchType,
                        @RequestParam(required = false) String searchValue,
                        @PageableDefault(size = 5, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
                        @SessionAttribute(name = SessionName.SESSION_LOGIN, required = false) Member loginMember,
                        Model model) {
        
        Page<PostDto> posts = postService.searchPosts(postType, searchType, searchValue, pageable);
        List<Integer> bar = paginationService.getPaginationBar(posts.getNumber(), posts.getTotalPages());

        model.addAttribute("postType", postType);
        model.addAttribute("searchTypes", SearchType.values());
        model.addAttribute("posts", posts);
        model.addAttribute("bar", bar);
        if (loginMember != null) {
            model.addAttribute("loginMember", true);
        }
        return "post/index";
    }


    @GetMapping("/{postId}")
    public String post(@PathVariable Long postId, Model model,
                       @SessionAttribute(name = SessionName.SESSION_LOGIN, required = false) Member loginMember,
                       HttpServletRequest request) {
        PostDto post = postService.findByIdUsingReadPost(postId);
        List<ReplyPrintDto> replies = replyService.findPostsReplies(postId);
        Long like = likesService.getLikes(postId);

        model.addAttribute("post", post);
        model.addAttribute("replies", replies);
        model.addAttribute( "repliesSize", replies.size());
        model.addAttribute("like", like);

        if (loginMember == null) {
            model.addAttribute("currPageURI", request.getRequestURI());
            return "post/post";
        } else {
            // 내 게시글 인지 확인
            if (loginMember.getId() == post.getWriterId()) {
                model.addAttribute("myPost", true);
            }
            // 북마크된 게시글인지 확인
            boolean bookmarkFlag = bookmarkService.existBookmark(postId, loginMember.getId());
            if (bookmarkFlag) {
                model.addAttribute("bookmarked", true);
            }
            model.addAttribute("reply", new ReplySaveDto(loginMember.getLoginId(), loginMember.getName()));
            model.addAttribute("currLoginId", loginMember.getLoginId());
            model.addAttribute("postId", post.getPostId());
            model.addAttribute("memberId", loginMember.getId());
            return "post/loginPost";
        }
    }

    @GetMapping("/likes")
    private String likes(@RequestParam(required = true) Long postId,
                         @RequestParam(required = true) Long memberId) {
        boolean result = likesService.clickLikes(postId, memberId);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/bookmark")
    private String bookmark(@RequestParam(required = true) Long postId,
                         @RequestParam(required = true) Long memberId) {
        bookmarkService.addBookmark(postId, memberId);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/new")
    public String postsCreateForm(@ModelAttribute("post") PostSaveDto postSaveDto, Model model) {
        model.addAttribute("postTypes", PostType.values());
        return "post/postSaveForm";
    }

    @PostMapping("/new")
    public String postsSave(@Validated @ModelAttribute("post") PostSaveDto postSaveDto,
                            BindingResult bindingResult,
                            Model model,
                            @SessionAttribute(name = SessionName.SESSION_LOGIN, required = false) Member loginMember) {
        if (bindingResult.hasErrors() || postSaveDto.getType() == null) {
            model.addAttribute("postTypes", PostType.values());
            return "post/postSaveForm";
        }
        postService.save(loginMember.getId(), postSaveDto);
        return "redirect:/posts";
    }

    @GetMapping("/{postId}/update")
    public String postUpdateForm(@PathVariable Long postId, Model model) {
        PostDto findPost = postService.findByIdUsingUpdatePost(postId);
        model.addAttribute("post", findPost);
        return "/post/postUpdateForm";
    }

    @PostMapping("/{postId}/update")
    public String postsUpdate(@PathVariable Long postId, @Validated @ModelAttribute("post") PostDto postDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("[postsUpdate] errors = " + bindingResult);
            return "/post/postUpdateForm";
        }
        postService.update(postId, postDto.getTitle(), postDto.getContent());
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/{postId}/delete")
    public String postDelete(@PathVariable Long postId) {
        postService.delete(postId);
        return "redirect:/posts";
    }
}
