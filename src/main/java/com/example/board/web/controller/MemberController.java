package com.example.board.web.controller;

import com.example.board.domain.Member;
import com.example.board.web.dto.member.MemberInfoDto;
import com.example.board.web.dto.member.MemberSaveDto;
import com.example.board.web.login.SessionName;
import com.example.board.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/new")
    public String memberSave(@ModelAttribute("member") MemberSaveDto memberSaveDto) {
        return "member/memberSaveForm";
    }

    @PostMapping("/new")
    public String save(@Validated @ModelAttribute("member") MemberSaveDto memberSaveDto,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/memberSaveForm";
        }

        boolean saved = memberService.save(memberSaveDto);
        if (!saved) {
            //필드값들이 @NotBlank 를 정상적으로 통과해도
            //bindingResult 에는 rejectValue 가 항상 담겨있다.
            bindingResult.reject("duplicateLoginId", null);
            return "member/memberSaveForm";
        }
        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String myPage() {
        return "member/myPage";
    }

    @GetMapping("/myinfo")
    public String myInfo(Model model,
                         @SessionAttribute(name = SessionName.SESSION_LOGIN, required = false) Member loginMember) {
        MemberInfoDto member = memberService.findMember(loginMember.getId());
        model.addAttribute("member", member);
        return "member/memberInfoForm";
    }


}
