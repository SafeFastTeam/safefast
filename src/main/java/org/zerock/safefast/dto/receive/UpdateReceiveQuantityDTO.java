package org.zerock.safefast.dto.receive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class UpdateReceiveQuantityDTO {
    private String procPlanNumber;
    private Integer receiveQuantity;
}
