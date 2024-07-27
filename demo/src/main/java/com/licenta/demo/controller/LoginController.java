package com.licenta.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/home")
    public String handleWelcome() {
        return "redirect:/";
    }

}
