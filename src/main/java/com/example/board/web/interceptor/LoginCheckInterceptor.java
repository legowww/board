package com.example.board.web.interceptor;

import com.example.board.web.login.SessionName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("[URI]" + requestURI);
        HttpSession session = request.getSession();
        if (!loginSessionCheck(session, SessionName.SESSION_LOGIN)) {
            response.sendRedirect("/login?redirectURI=" + requestURI);
            return false;
        }
        return true;
    }

    private boolean loginSessionCheck(HttpSession session, String sessionName) {
        if (session == null || session.getAttribute(sessionName) == null) {
            return false;
        }
        return true;
    }
}
