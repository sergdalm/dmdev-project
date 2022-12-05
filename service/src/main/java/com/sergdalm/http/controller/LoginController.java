package com.sergdalm.http.controller;

import com.sergdalm.dto.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "users/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("login") LoginDto loginDto) {
        return "redirect:/login";
    }
}
