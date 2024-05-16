package org.zerock.safefast.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicUpdate
public class CoOpCompany {

    @Id
    @Column
    private String businessNumber;

    @Column
    private String companyName;

    @Column
    private String companyAddress;

    @Column
    private String manager;

    @Column
    private String managerTel;

    @Column
    private String managerEmail;

    @Column
    private String companyAccount;

}
