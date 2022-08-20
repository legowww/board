package com.example.board;

import com.example.board.domain.Member;
import com.example.board.domain.type.PostType;
import com.example.board.web.login.SessionName;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionName.SESSION_LOGIN, required = false) Member loginMember, Model model) {
        model.addAttribute("postTypes", PostType.values());

        if (loginMember == null) {
            return "home";
        }

        String loginMemberName = loginMember.getName();
        model.addAttribute("memberName", loginMemberName);
        return "loginHome";
    }
}
