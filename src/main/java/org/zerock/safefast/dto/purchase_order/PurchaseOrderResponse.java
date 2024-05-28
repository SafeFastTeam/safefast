package org.zerock.safefast.dto.purchase_order;

import lombok.Data;
import org.zerock.safefast.entity.CoOpCompany;
import org.zerock.safefast.entity.Item;
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
    private CoOpCompany coOpCompany;
    private Item item;
    private String businessNumber;
    private String itemCode;


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
        this.procPlanNumber = purchaseOrder.getProcPlanNumber();
        this.businessNumber = purchaseOrder.getBusinessNumber();
        this.itemCode = purchaseOrder.getItemCode();
        // 다른 필드들 초기화...
    }

    // Getters and Setters
    public String getPurchOrderNumber() {
        return purchOrderNumber;
    }

    public void setPurchOrderNumber(String purchOrderNumber) {
        this.purchOrderNumber = purchOrderNumber;
    }

    public String getPurchOrderDate() {
        return purchOrderDate;
    }

    public void setPurchOrderDate(String purchOrderDate) {
        this.purchOrderDate = purchOrderDate;
    }

    public String getReceiveDuedate() {
        return receiveDuedate;
    }

    public void setReceiveDuedate(String receiveDuedate) {
        this.receiveDuedate = receiveDuedate;
    }

    public Integer getPurchOrderQuantity() {
        return purchOrderQuantity;
    }

    public void setPurchOrderQuantity(Integer purchOrderQuantity) {
        this.purchOrderQuantity = purchOrderQuantity;
    }

    public String getProcPlanNumber() {
        return procPlanNumber;
    }

    public void setProcPlanNumber(String procPlanNumber) {
        this.procPlanNumber = procPlanNumber;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    // 추가 필드에 대한 Getters and Setters

}