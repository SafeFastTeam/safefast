package org.zerock.safefast.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

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

    private LocalDate progCheckDate;

    private String progCheckResult;

    private Integer CompletedQuantity;

    private String supplementation;

    @ManyToOne
    @JoinColumn(name = "procPlanNumber")
    private ProcurementPlan procurementPlan;

    @ManyToOne
    @JoinColumn(name = "purchOrderNumber")
    @JsonBackReference
    private PurchaseOrder purchaseOrder;

}
