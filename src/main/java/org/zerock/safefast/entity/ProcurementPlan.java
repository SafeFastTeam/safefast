package org.zerock.safefast.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "receives")
@DynamicUpdate
public class ProcurementPlan {

    @Id
    @Column(name = "procPlanNumber")
    private String procPlanNumber;

    @Column
    private Integer procQuantity;

    @Column(name = "procDuedate", nullable = false)
    private LocalDate procDuedate;

    @Column
    private Integer procProgress;

    @Column
    private LocalDate procRegisterDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "itemCode", referencedColumnName = "itemCode", insertable = false, updatable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "productCode", referencedColumnName = "productCode", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "businessNumber", referencedColumnName = "businessNumber", insertable=false, updatable=false)
    private CoOpCompany coOpCompany;

    @Column
    private String itemCode;

    @Column
    private String productCode;

    @Column
    private String businessNumber;
}

