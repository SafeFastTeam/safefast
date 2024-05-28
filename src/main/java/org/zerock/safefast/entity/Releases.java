package org.zerock.safefast.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicUpdate
public class Releases {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer releaseNumber;

    @Column
    private LocalDate releaseDate;

    @Column
    private Integer releaseQuantity;

    @ManyToOne
    @JoinColumn(name = "itemCode")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "quantityId")
    private Quantity quantity;

}
