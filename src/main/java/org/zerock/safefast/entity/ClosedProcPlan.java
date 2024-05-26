package org.zerock.safefast.entity;

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
@ToString
@DynamicUpdate
public class ClosedProcPlan {

    @Id
    @Column
    private String procPlanNumber;

    @Column
    private String companyName;

    @Column
    private String businessNumber;

    @Column
    private String itemCode;

    @Column
    private String itemName;

    @Column
    private LocalDate purchOrderDate;

}
