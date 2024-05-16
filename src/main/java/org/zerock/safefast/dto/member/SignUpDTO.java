package org.zerock.safefast.dto.member;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {

    private Integer memberNumber;

    private String empNumber;

    private String password;

    private String name;

    private String email;
}
