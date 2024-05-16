package org.zerock.safefast.service.login;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.safefast.dto.member.SignUpDTO;
import org.zerock.safefast.entity.Member;
import org.zerock.safefast.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Integer join(SignUpDTO signUpDTO) {

        Member member = Member.builder()
                .memberNumber(signUpDTO.getMemberNumber())
                .empNumber(signUpDTO.getEmpNumber())
                .name(signUpDTO.getName())
                .password(passwordEncoder.encode(signUpDTO.getPassword()))
                .email(signUpDTO.getEmail())
                .build();
        return memberRepository.save(member).getMemberNumber();
    }

    @Override
    public SignUpDTO entityDTO(Member member) {
        SignUpDTO signUpDTO = SignUpDTO.builder()
                .memberNumber(member.getMemberNumber())
                .empNumber(member.getEmpNumber())
                .name(member.getName())
                .password(member.getPassword())
                .email(member.getEmail())
                .build();
        return signUpDTO;
    }

    @Override
    public boolean isIdDuplicated(String empNumber) {
        Member existingMember = memberRepository.findByEmpNumber(empNumber);

        return existingMember != null;
    }
}
