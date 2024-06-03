package org.zerock.safefast.dto.inventory_management;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryValueDTO {
    private String category;
    private Double inventoryValue;
}