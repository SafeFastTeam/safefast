package org.zerock.safefast.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicUpdate
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer memberNumber;

    @Column(unique = true) // 아이디는 중복되지 않아야 함
    private String empNumber;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String email;

    // Authorities 테이블과의 연결
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Authority> authorities;

}
