package org.zerock.safefast.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "procurementPlan")
@DynamicUpdate
public class Receive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer receiveNumber;

    @Column
    @CreatedDate
    private LocalDateTime receiveDate;

    @Column
    private Integer receiveQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procPlanNumber")
    private ProcurementPlan procurementPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemCode")
    private Item item;
}
