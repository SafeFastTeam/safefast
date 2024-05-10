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

    @Column
    private String itemCode;
}
