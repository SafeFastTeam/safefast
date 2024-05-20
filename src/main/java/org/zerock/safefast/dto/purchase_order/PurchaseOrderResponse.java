package org.zerock.safefast.dto.purchase_order;

import lombok.Data;

@Data
public class PurchaseOrderResponse {
    private String purchOrderNumber;
    private String purchOrderDate;
    private String receiveDuedate;
    private int purchOrderQuantity;
    private int procCheckResult;
    private int procCheckOrder;

    // 생성자, 게터, 세터 생략 (필요 시 추가)
}