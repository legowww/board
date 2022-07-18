package com.example.board;

import com.example.board.domain.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = "SESSION_MEMBER", required = false) Member loginMember, Model model) {
        if (loginMember == null) {
            return "home";
        }
        String loginMemberName = loginMember.getName();
        model.addAttribute("memberName", loginMemberName);
        return "loginHome";
    }
}
