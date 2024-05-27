package org.zerock.safefast.dto.purchase_order;


import java.time.LocalDate;

public class PurchaseOrderRequest {
    public String procPlanNumber;
    public int purchaseOrderQuantity;
    public String receiveDuedate;

    // Getters and Setters

    public String getProcPlanNumber() {
        return procPlanNumber;
    }

    public void setProcPlanNumber(String procPlanNumber) {
        this.procPlanNumber = procPlanNumber;
    }

    public int getPurchaseOrderQuantity() {
        return purchaseOrderQuantity;
    }

    public void setPurchaseOrderQuantity(int purchaseOrderQuantity) {
        this.purchaseOrderQuantity = purchaseOrderQuantity;
    }

    public LocalDate getReceiveDuedate() {
        return LocalDate.parse(receiveDuedate);
    }

    public void setReceiveDuedate(LocalDate receiveDuedate) {
        this.receiveDuedate = String.valueOf(receiveDuedate);
    }

    public Integer getPurchOrderQuantity() {
        return purchaseOrderQuantity;
    }

    public String getNote() {
        return purchaseOrderQuantity + "";
    }
}