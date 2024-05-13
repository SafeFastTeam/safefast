package org.zerock.safefast.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.safefast.entity.Member;
import org.zerock.safefast.service.LoginService;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Log4j2
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception, Model model,
                            HttpSession session) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        // 세션에 로그인 상태를 저장합니다. 로그인이 성공했을 때 이를 확인하여 리다이렉트합니다.
        session.setAttribute("isLoggedIn", true);

        return "member/home";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "member/login_form";
    }

    @PostMapping("/reset_password_form")
    public String resetPassword(@RequestParam("empNumber") String empNumber,
                                @RequestParam("email") String email,
                                HttpServletResponse response) throws IOException {
        Member member = loginService.findByEmpNumberAndEmail(empNumber, email);

        if (member != null) {
            loginService.sendPasswordResetMail(email);
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println("<script>alert('비밀번호 재설정 이메일이 전송되었습니다.');</script>");
            writer.flush();
            return "redirect:/member/login"; // 수정된 부분: 로그인 페이지로 리다이렉트
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println("<script>alert('아이디 또는 이메일 주소가 올바르지 않습니다.');</script>");
            writer.flush();
            return "member/reset_password_form";
        }
    }

    @GetMapping("/forgot_id")
    public String showForgotIdForm() {
        return "admin/member/forgot_id_form";
    }

    @PostMapping("/forgot_id")
    public String processForgotIdForm(@RequestParam("name") String name, @RequestParam("email") String email, HttpServletResponse response) throws IOException {
        Member member = loginService.findByNameAndEmail(name, email);

        if (member != null) {
            String userId = member.getEmpNumber();
            String subject = "Your ID Recovery";
            String text = "Your ID is: " + userId;
            loginService.sendMail(email, subject, text);
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println("<script>alert('가입하신 이메일 주소로 아이디를 전송했습니다.');</script>");
            writer.flush();
            return "member/forgot_id_form";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println("<script>alert('해당 이메일 주소와 연결된 사용자가 없습니다.');</script>");
            writer.flush();
            return "member/forgot_id_form";
        }
    }
}