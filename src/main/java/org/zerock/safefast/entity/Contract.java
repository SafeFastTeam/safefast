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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "businessNumber")
    private CoOpCompany coOpCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemCode")
    private Item item;

}