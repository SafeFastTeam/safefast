package org.zerock.safefast.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/index")
    public String showIndex() {
        return "/index";
    }

    @GetMapping("/home")
    public String showHome() {
        return "/member/home";
    }
}
