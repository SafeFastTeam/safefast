package org.zerock.safefast.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

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
    private Integer LENGTH;

    @Column
    private Integer height;

    @Column
    private String material;

    @Column
    private String blueprintSaveName;

    @Column
    private String blueprintOriginName;

    @Column
    private String unitCode;

    @Column
    private String assyCode;

    @Column
    private String partCode;
}
