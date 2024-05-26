package org.zerock.safefast.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
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
    private LocalDate receiveDate;

    @Column
    private Integer receiveQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchOrderNumber")
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemCode")
    private Item item;
}
