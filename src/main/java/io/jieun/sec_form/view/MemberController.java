package io.jieun.sec_form.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    @GetMapping("/signin")
    public String showSigninForm() {
        return "signin";
    }
}
