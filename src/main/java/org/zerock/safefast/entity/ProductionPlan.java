package org.zerock.safefast.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDate prodStartDate;

    @Column
    private LocalDate prodEndDate;

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

    @OneToMany(mappedBy = "productionPlan")
    private List<Quantity> quantity;

    @ManyToOne
    @JoinColumn(name = "businessNumber")
    private CoOpCompany coOpCompany;
}
