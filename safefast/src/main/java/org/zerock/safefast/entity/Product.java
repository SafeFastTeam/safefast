package org.zerock.safefast.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicUpdate
public class Product {
    @Id
    @Column
    private String productCode;

    private String productName;

    @OneToMany(mappedBy = "product")
    private List<ProcurementPlan> procurementPlans;
}
