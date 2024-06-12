package org.zerock.safefast.controller.login;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.safefast.dto.member.SignUpDTO;
import org.zerock.safefast.service.login.SignUpService;

import java.io.PrintWriter;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Log4j2
public class SignUpController {

    private final SignUpService signUpService;

    @GetMapping("/create_account_form")
    public String createMember() {
        log.info("Create account form");

        return "/member/create_account_form";
    }

    @PostMapping("/create_account_form")
    public String createMember(SignUpDTO signUpDTO, HttpServletResponse response) throws Exception {
        if (signUpService.isIdDuplicated(signUpDTO.getEmpNumber())) {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.flush();
            return "/member/create_account_form";
        } else {
            signUpService.join(signUpDTO);
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<script>alert('회원가입이 완료됐습니다.');</script>");
            out.flush();
            return "/member/login_form";
        }
    }
}
