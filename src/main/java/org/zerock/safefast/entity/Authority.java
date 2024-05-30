package org.zerock.safefast.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "emp_number")
    private Member member;

    @Column
    private String authority; // 권한 정보

    public Authority(Member member, String authority) {
        this.member = member;
        this.authority = authority;
    }
}
