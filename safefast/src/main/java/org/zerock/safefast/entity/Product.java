package org.zerock.safefast.entity;

import java.util.List;
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
public class Product {
    @Id
    @Column
    private String productCode;

    private String productName;
}
