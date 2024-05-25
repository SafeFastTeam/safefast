package org.zerock.safefast.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"unit", "assy", "part", "contracts"})
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Item {
    @Id
    @Column
    private String itemCode;

    @Column
    private String itemName;

    @Column
    private Integer width;

    @Column
    private Integer length;

    @Column
    private Integer height;

    @Column
    private String material;

    @Column
    private String blueprintSaveName;

    @Column
    private String blueprintOriginName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unitCode")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assyCode")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Assy assy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partCode")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Part part;

    @OneToMany(mappedBy = "item")
    private List<Contract> contracts;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private List<Contract> contract;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private List<PurchaseOrder> purchaseOrder;
}
