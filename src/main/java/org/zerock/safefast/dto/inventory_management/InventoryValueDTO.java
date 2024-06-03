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

    public InventoryValueDTO(String itemCode, String itemName, Double inventoryValue) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.inventoryValue = inventoryValue;
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