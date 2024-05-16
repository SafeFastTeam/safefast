package org.zerock.safefast.service.login;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.safefast.dto.member.LoginDTO;
import org.zerock.safefast.entity.Member;
import org.zerock.safefast.repository.MemberRepository;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Override
    public Member authenticate(LoginDTO loginDTO) {
        String empNumber = loginDTO.getEmpNumber();
        String password = loginDTO.getPassword();

        Member member = memberRepository.findByEmpNumber(empNumber);

        if (member != null && passwordEncoder.matches(password, member.getPassword())) {

            return member;
        }
        return null;
    }

    @Override
    public void sendPasswordResetMail(String email) {
        String temporaryPassword = generateTemporaryPassword();

        boolean mailsent = sendMail(email, "비밀번호 재설정 안내", "임시 비밀번호: " + temporaryPassword);

        if (mailsent) {
            Member member = memberRepository.findByEmail(email);

            if (member != null) {
                member.setPassword(passwordEncoder.encode(temporaryPassword));
                memberRepository.save(member);
            }
        }else {
            // 이메일 전송 실패 처리
            // 예: 로깅 또는 사용자에게 메시지 전송
        }
    }

    @Override
    public Member findByEmpNumberAndEmail(String empNumber, String email) {
        return memberRepository.findByEmpNumberAndEmail(empNumber, email);
    }

    private String generateTemporaryPassword() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[8];
        random.nextBytes(bytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public boolean sendMail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            return true;
        } catch (MailException e) {
            return false;
        }
    }

    @Override
    public Member findByNameAndEmail(String name, String email) {
        return memberRepository.findByNameAndEmail(name, email);
    }

}
