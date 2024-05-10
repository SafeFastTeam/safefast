package org.zerock.safefast.dto.member;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private String empNumber;

    private String password;

    private String email;
}
