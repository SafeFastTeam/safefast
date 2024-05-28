package org.zerock.safefast.dto.purchase_order;


import org.zerock.safefast.entity.CoOpCompany;
import org.zerock.safefast.entity.Item;

import java.time.LocalDate;

public class PurchaseOrderRequest {
    public String procPlanNumber;
    public int purchaseOrderQuantity;
    public String receiveDuedate;
    private CoOpCompany coOpCompany;
    private Item item;
    private String businessNumber;
    private String itemCode;

    // Getters and Setters

    public String getProcPlanNumber() {
        return procPlanNumber;
    }

    public void setProcPlanNumber(String procPlanNumber) {
        this.procPlanNumber = procPlanNumber;
    }

    public void setPurchaseOrderQuantity(int purchaseOrderQuantity) {
        this.purchaseOrderQuantity = purchaseOrderQuantity;
    }

    public LocalDate getReceiveDuedate() {
        return LocalDate.parse(receiveDuedate);
    }

    public Integer getPurchOrderQuantity() {
        return purchaseOrderQuantity;
    }

    public String getNote() {
        return purchaseOrderQuantity + "";
    }

    public CoOpCompany getCoOpCompany() {
        return coOpCompany;
    }

    public void setCoOpCompany(CoOpCompany coOpCompany) {
        this.coOpCompany = coOpCompany;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    public String getBusinessNumber() {
        if (coOpCompany != null) {
            return coOpCompany.getBusinessNumber();
        }
        return null;
    }
    public String getItemCode() {
        if (item != null) {
            return item.getItemCode();
        }
        return null;
    }
}