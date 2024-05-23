package org.zerock.safefast.dto.receive;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveDTO {
    private Integer receiveNumber;

    private LocalDateTime receiveDate;

    private Integer receiveQuantity;

    private String purchOrderNumber;

    private String itemCode;


}
