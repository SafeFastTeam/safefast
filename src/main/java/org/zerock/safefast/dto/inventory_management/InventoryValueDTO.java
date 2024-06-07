package org.zerock.safefast.dto.inventory_management;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryValueDTO {
    private String itemCode;
    private String itemName;
    private String category;
    private Double inventoryValue;
    private int receiveQuantity; // 추가
    private Double itemPrice;
    private int allQuantity;

    public InventoryValueDTO(String itemCode, String itemName, Double inventoryValue) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.inventoryValue = inventoryValue;
    }

    public InventoryValueDTO(String itemCode, String itemName, Double inventoryValue, int receiveQuantity, Double itemPrice, int allQuantity) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.inventoryValue = inventoryValue;
        this.receiveQuantity = receiveQuantity;
        this.itemPrice = itemPrice;
        this.allQuantity = allQuantity;
    }

    // Getters and setters
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getInventoryValue() {
        return inventoryValue;
    }

    public void setInventoryValue(Double inventoryValue) {
        this.inventoryValue = inventoryValue;
    }
}