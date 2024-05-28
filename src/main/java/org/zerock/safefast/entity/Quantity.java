package org.zerock.safefast.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicUpdate
public class Quantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quantityId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_code")
    private Item item;

    @Column
    private Integer allQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prodPlanCode")
    private ProductionPlan productionPlan;
}
