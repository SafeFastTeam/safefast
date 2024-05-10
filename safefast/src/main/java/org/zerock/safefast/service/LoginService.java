package org.zerock.safefast.service;

import org.zerock.safefast.dto.member.LoginDTO;
import org.zerock.safefast.entity.Member;

public interface LoginService {
    Member authenticate(LoginDTO loginDTO);

    void sendPasswordResetMail(String email);

    Member findByEmpNumberAndEmail(String empNumber, String email);

    boolean sendMail(String to, String subject, String body);

    Member findByNameAndEmail(String name, String email);
}
