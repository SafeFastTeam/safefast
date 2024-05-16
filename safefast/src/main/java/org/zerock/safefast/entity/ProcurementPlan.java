package org.zerock.safefast.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

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
    @Column(name = "procPlanNumber")
    private String procPlanNumber;

    @Column
    private Integer procQuantity;

    @Column
    private LocalDateTime procDueDate;

    @Column
    private Integer procProgress;

    @Column
    private LocalDateTime procRegisterDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "itemCode", referencedColumnName = "itemCode", insertable = false, updatable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "productCode", referencedColumnName = "productCode", insertable = false, updatable = false)
    private Product product;

    @Column
    private String itemCode;

    public void generateProcPlanNumber() {
        // 임의의 키 생성 로직을 구현하여 값을 설정
        this.procPlanNumber = generateUniqueKey();
    }

    private String generateUniqueKey() {
        // 임의의 키 생성 로직을 구현하여 반환
        // 예: UUID.randomUUID()를 사용하여 UUID 생성
        return UUID.randomUUID().toString();
    }

}

