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
public class Receive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer receiveNumber;

    @Column
    private LocalDateTime receiveDate;

    @Column
    private Integer receiveQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procPlanNumber")
    private ProcurementPlan procurementPlan;

    @Column
    private String itemCode;
}
