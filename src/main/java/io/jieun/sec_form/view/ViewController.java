package io.jieun.sec_form.view;

import io.jieun.sec_form.app.MemberService;
import io.jieun.sec_form.dto.SignUpForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ViewController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(SignUpForm signUpForm) { //model attirbute를 사용해줘야됨
        memberService.save(signUpForm);
        return "redirect:/";
    }

    @GetMapping("/signin")
    public String showSigninForm() {
        return "signin";
    }
}
