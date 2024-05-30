package org.zerock.safefast.controller.login;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.safefast.dto.member.LoginDTO;
import org.zerock.safefast.entity.Member;
import org.zerock.safefast.service.login.LoginService;


import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Log4j2
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String showLogin() {
        return "member/login_form";
    }

    @PostMapping("/login")
    public String processLogin(LoginDTO loginDTO, HttpSession session, HttpServletResponse response) throws IOException {
        Member member = loginService.authenticate(loginDTO);

        if (member != null) {
            // 세션에 사용자 정보 저장
            session.setAttribute("member", member);
            return "/index"; // 로그인 성공 시 이동할 페이지
        } else {
            // 로그인 실패 시 처리
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println("<script>alert('아이디 또는 비밀번호가 올바르지 않습니다.');</script>");
            writer.flush();
            return "member/login_form";
        }
    }

    @GetMapping("/home")
    public String home() {
        return "/member/home";
    }

    @GetMapping("/reset_password_form")
    public String resetPasswordForm() {
        return "member/reset_password_form";
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
            return "member/login_form";
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
        return "member/forgot_id_form";
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
            return "member/login_form";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println("<script>alert('해당 이메일 주소와 연결된 사용자가 없습니다.');</script>");
            writer.flush();
            return "member/forgot_id_form";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/member/login";
    }
}
