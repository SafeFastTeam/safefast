package org.zerock.safefast.service;

import org.zerock.safefast.dto.member.SignUpDTO;
import org.zerock.safefast.entity.Member;

public interface SignUpService {
    Integer join(SignUpDTO signUpDTO);

    SignUpDTO entityDTO(Member member);

    boolean isIdDuplicated(String empNumber);
}
