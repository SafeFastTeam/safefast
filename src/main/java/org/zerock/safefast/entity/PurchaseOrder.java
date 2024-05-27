package org.zerock.safefast.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
@ToString(exclude = {"coOpCompany", "item"})
@DynamicUpdate
public class PurchaseOrder {
    @Id
    @Column
    private String purchOrderNumber;

    @Column
    private LocalDate purchOrderDate = LocalDate.now();

    @Column
    private Integer purchOrderQuantity;

    @Column
    private String note;

    @Column
    private LocalDate receiveDuedate;

    @Column
    private Integer purchProgress;

    @Column
    private String procPlanNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "businessNumber", referencedColumnName = "businessNumber")
    private CoOpCompany coOpCompany;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "itemCode")
    private Item item;

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ProgressCheckItem> progressCheckItems;

    // Getter and Setter methods

}
