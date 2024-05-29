package org.zerock.safefast.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicUpdate
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "businessNumber")
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

    @OneToMany(mappedBy = "coOpCompany")
    private List<PurchaseOrder> purchaseOrders;
}
