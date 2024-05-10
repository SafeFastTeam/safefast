package org.zerock.safefast.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Purchase_order {
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
}
