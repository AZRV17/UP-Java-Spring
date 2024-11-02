package com.example.finalfront.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final HttpSession session;

    public TokenService(HttpSession session) {
        this.session = session;
    }

    public void saveToken(String token) {
        session.setAttribute("jwtToken", token);
    }

    public String getToken() {
        String token = (String) session.getAttribute("jwtToken");
        return token;
    }

    public void removeToken() {
        session.removeAttribute("jwtToken");
    }
}
