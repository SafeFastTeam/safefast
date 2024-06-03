package org.zerock.safefast.dto.contract;

import lombok.Builder;
import lombok.Data;
import org.zerock.safefast.entity.Contract;

@Data
@Builder
public class ContractDTO {
    private String contractNumber;
    private String businessNumber;
    private String itemName;
    private String itemCode;
    private String companyName;
    private String contractOriginName;
    private String contractSaveName;
    private int itemPrice;
    private int leadTime;
    private String note;
    // 필요한 다른 속성들을 추가할 수 있습니다.


}
