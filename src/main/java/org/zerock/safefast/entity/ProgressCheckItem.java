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
public class ProgressCheckItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column

    private Integer progCheckNumber;

    private Integer progCheckOrder;

    private LocalDateTime progCheckDate;

    private String progCheckResult;

    private Integer CompletedQuantity;

    private String supplementation;

    private String purchOrderNumber;

    private String procPlanNumber;
}
