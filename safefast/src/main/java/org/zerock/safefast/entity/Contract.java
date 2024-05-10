package org.zerock.safefast.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Contract {

    @Id
    @Column
    private String contractNumber;

    @Column
    private LocalDateTime contractDate;

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

    @Column
    private String businessNumber;

    @Column
    private String itemCode;

}
