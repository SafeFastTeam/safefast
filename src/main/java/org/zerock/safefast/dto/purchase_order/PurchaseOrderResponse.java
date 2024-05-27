package org.zerock.safefast.dto.purchase_order;

import lombok.Data;
import org.zerock.safefast.entity.PurchaseOrder;

@Data
public class PurchaseOrderResponse {
    private String purchOrderNumber;
    private String purchOrderDate;
    private String receiveDuedate;
    private int purchOrderQuantity;
    private int procCheckResult;
    private int procCheckOrder;
    private String procPlanNumber;

    // 생성자, 게터, 세터 생략 (필요 시 추가)

    // 기본 생성자
    public PurchaseOrderResponse() {
    }

    // PurchaseOrder를 인수로 받는 생성자
    public PurchaseOrderResponse(PurchaseOrder purchaseOrder) {
        this.purchOrderNumber = purchaseOrder.getPurchOrderNumber();
        this.purchOrderDate = purchaseOrder.getPurchOrderDate().toString();
        this.receiveDuedate = purchaseOrder.getReceiveDuedate().toString();
        this.purchOrderQuantity = purchaseOrder.getPurchOrderQuantity();
        // 다른 필드들 초기화...
    }

}