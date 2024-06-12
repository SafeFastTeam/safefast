package org.zerock.safefast.controller.progress;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/modal1")
public class Modal1Controller {

    @GetMapping
    public String showModal1(Model model) {
        // 모달 1에 필요한 데이터를 모델에 추가
        model.addAttribute("modalData", "모달 1 데이터");
        return "/modal/modal1"; // 모달 1의 Thymeleaf 템플릿 경로
    }
}