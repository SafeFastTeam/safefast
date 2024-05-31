package org.zerock.safefast.entity;

import com.fasterxml.jackson.annotation.*;
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
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "progCheckNumber")
public class ProgressCheckItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer progCheckNumber;

    private Integer progCheckOrder;

    private LocalDate progCheckDate;

    @Column(nullable = false)
    private String progCheckResult = "0";

    @Column
    private Integer completedQuantity = 0;

    private String supplementation;

    @ManyToOne
    @JoinColumn(name = "procPlanNumber")
    private ProcurementPlan procurementPlan;

    @ManyToOne
    @JoinColumn(name = "purchOrderNumber")
    @JsonBackReference
    private PurchaseOrder purchaseOrder;

}
