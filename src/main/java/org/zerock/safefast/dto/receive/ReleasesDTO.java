package org.zerock.safefast.dto.receive;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReleasesDTO {
    private Integer releaseNumber;

    private LocalDate releaseDate;

    private Integer releaseQuantity;

    private String itemCode;

    private Integer id;

}
