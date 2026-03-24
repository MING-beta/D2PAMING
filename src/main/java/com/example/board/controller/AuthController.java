package com.example.board.controller;

import com.example.board.dto.SignupForm;
import com.example.board.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        if (!model.containsAttribute("signupForm")) {
            model.addAttribute("signupForm", new SignupForm());
        }
        return "auth/login";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        return "redirect:/auth/login"; // redirect to integrated page
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute SignupForm signupForm,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }

        if (!signupForm.getPassword().equals(signupForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm",
                    "비밀번호가 일치하지 않습니다.");
            return "auth/login";
        }

        try {
            memberService.signup(signupForm.getUsername(), signupForm.getPassword());
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("username", "error.username", e.getMessage());
            return "auth/login";
        }

        return "redirect:/auth/login?signup";
    }
}
