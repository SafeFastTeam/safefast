package org.zerock.safefast.dto.receive;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcurementDTO {
    private String procPlanNumber;
    private Integer procQuantity;
    private LocalDateTime procDuedate;
    private Integer procProgress;
    private LocalDateTime procRegisterDate;
    private String itemCode;
}
