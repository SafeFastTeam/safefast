package org.zerock.safefast.entity;

import java.util.List;
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
@ToString
@DynamicUpdate
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
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assyCode")
    private Assy assy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partCode")
    private Part part;

    @OneToMany(mappedBy = "item")
    private List<Contract> contracts;

}
