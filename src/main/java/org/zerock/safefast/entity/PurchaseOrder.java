package org.zerock.safefast.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicUpdate
public class PurchaseOrder {
    @Id
    @Column
    private String purchOrderNumber;

    @Column
    private LocalDateTime purchOrderDate;

    @Column
    private Integer purchOrderQuantity;

    @Column
    private String note;

    @Column
    private LocalDateTime receiveDuedate;

    @Column
    private String procPlanNumber;

    @ManyToOne
    @JoinColumn(name = "businessNumber", referencedColumnName = "businessNumber")
    private CoOpCompany coOpCompany;
}
