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
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_code", referencedColumnName = "itemCode")
    private Item item;

    @Builder.Default
    private int quantityAvailable = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiveNumber")
    private Receive receive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "releasesNumber")
    private Releases releases;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contractNumber")
    private Contract contract;

}
