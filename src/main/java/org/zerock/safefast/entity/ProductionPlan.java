package org.zerock.safefast.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

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

    @ManyToOne
    @JoinColumn(name = "businessNumber")
    private CoOpCompany coOpCompany;

    // @PrePersist를 사용하여 엔티티가 영구 저장되기 전에 prodPlanCode를 생성합니다.
    @PrePersist
    public void generateProdPlanCode() {
        if (prodPlanCode == null) {
            String newProdPlanCode = generateNewProdPlanCode();
            this.prodPlanCode = newProdPlanCode;
        }
    }

    private String generateNewProdPlanCode() {
        return "PROD-001"; // 임시로 기본값 "PROD-001"을 반환합니다.
    }

    //Getter Setter 메서드
    public void setProduct(Product product) {
        this.product = product;
    }
}
