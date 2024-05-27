package org.zerock.safefast.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "item")
@DynamicUpdate
public class Contract {

    @Id
    @Column
    private String contractNumber;

    @Column
    private LocalDate contractDate;

    @Column
    private String contractSaveName;

    @Column
    private String contractOriginName;

    @Column
    private String note;

    @Column
    private Integer leadTime;

    @Column
    private Integer itemPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "businessNumber")
    @JsonIgnore
    private CoOpCompany coOpCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemCode")
    @JsonIgnore
    private Item item;


    @PrePersist
    public void ensureId() {
        if (this.contractNumber == null) {
            this.contractNumber = generateUniqueKey();
        }
    }

    private String generateUniqueKey() {
        // 예시로 간단한 번호 생성 로직 구현 (실제 구현은 서비스 레이어에서 할 수 있음)
        return "CON-" + String.format("%03d", (int) (Math.random() * 1000));
    }

}
