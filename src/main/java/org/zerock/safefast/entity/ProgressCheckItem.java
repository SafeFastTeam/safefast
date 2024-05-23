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
public class ProgressCheckItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer progCheckNumber;

    private Integer progCheckOrder;

    private String progCheckDate;

    private String progCheckResult;

    private Integer CompletedQuantity;

    private String supplementation;

    @ManyToOne
    @JoinColumn(name = "procPlanNumber")
    private ProcurementPlan procurementPlan;

    @ManyToOne
    @JoinColumn(name = "purchOrderNumber")
    private PurchaseOrder purchaseOrder;
}
