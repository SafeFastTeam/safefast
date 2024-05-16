package org.zerock.safefast.controller.progress;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/modal2")
public class Modal2Controller {

    @GetMapping
    public String showModal2(Model model) {
        // 모달 2에 필요한 데이터를 모델에 추가
        model.addAttribute("modalData", "모달 2 데이터");
        return "modal/modal2"; // 모달 2의 Thymeleaf 템플릿 경로
    }
}