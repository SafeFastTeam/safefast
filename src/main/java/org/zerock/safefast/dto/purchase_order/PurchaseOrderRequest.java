package org.zerock.safefast.dto.purchase_order;


import org.zerock.safefast.entity.CoOpCompany;
import org.zerock.safefast.entity.Item;

import java.time.LocalDate;
import java.util.Date;

public class PurchaseOrderRequest {
    public String procPlanNumber;
    public int purchOrderQuantity;
    public LocalDate receiveDuedate;
    private CoOpCompany coOpCompany;
    private Item item;
    private String businessNumber;
    private String itemCode;
    private LocalDate purchOrderDate;

    // Getters and Setters

    public String getProcPlanNumber() {
        return procPlanNumber;
    }

    public void setProcPlanNumber(String procPlanNumber) {
        this.procPlanNumber = procPlanNumber;
    }



    public LocalDate getReceiveDuedate() {
        return receiveDuedate;
    }

    public void setReceiveDuedate(LocalDate receiveDuedate) {
        this.receiveDuedate = receiveDuedate;
    }

    public int getPurchOrderQuantity() {
        return purchOrderQuantity;
    }

    public void setPurchOrderQuantity(int purchaseOrderQuantity) {
        this.purchOrderQuantity = purchaseOrderQuantity;
    }

    public String getNote() {
        return purchOrderQuantity + "";
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

    public LocalDate getPurchOrderDate() {
        return purchOrderDate;
    }

    public void setPurchOrderDate(LocalDate purchOrderDate) {
        this.purchOrderDate = purchOrderDate;
    }
}