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
public class ProductionPlan {
    @Id
    @Column
    private String prodPlanCode;

    @Column
    private LocalDateTime prodStartDate;

    @Column
    private LocalDateTime prodEndDate;

    @Column
    private Integer prodQuantity;

    @Column
    private Integer procureTerm;

    @ManyToOne
    @JoinColumn(name = "itemCode")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;

}