package org.zerock.safefast.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
public class PurchaseOrder {
    @Id
    @Column
    private String purchOrderNumber;

    @Column
    private LocalDateTime purchOrderDate = LocalDateTime.now();

    @Column
    private Integer purchOrderQuantity;

    @Column
    private String note;

    @Column
    private LocalDateTime receiveDuedate;

    @Column
    private String procPlanNumber;

    @ManyToOne
    @JoinColumn(name = "businessNumber", referencedColumnName = "businessNumber")
    private CoOpCompany coOpCompany;

    @PrePersist
    public void ensureId() {
        if (this.purchOrderNumber == null) {
            this.purchOrderNumber = generateUniqueKey();
        }
    }

    private String generateUniqueKey() {
        // 임의의 키 생성 로직을 구현하여 반환
        // 예: UUID.randomUUID()를 사용하여 UUID 생성
        // UUID를 생성하여 첫 8자리만 반환
        return UUID.randomUUID().toString().substring(0, 8);
    }

    // Getter and Setter methods

}
