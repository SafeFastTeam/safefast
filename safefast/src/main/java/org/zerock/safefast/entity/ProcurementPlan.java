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
public class ProcurementPlan {
    @Id
    @Column
    private String procPlanNumber;

    @Column
    private Integer procQuantity;

    @Column
    private LocalDateTime procDuedate;

    @Column
    private Integer procProgress;

    @Column
    private LocalDateTime procRegisterDate;

    @ManyToOne
    @JoinColumn(name = "itemCode", referencedColumnName = "itemCode", insertable = false, updatable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "productCode", referencedColumnName = "productCode", insertable = false, updatable = false)
    private Product product;

    @Column
    private String itemCode;
}
