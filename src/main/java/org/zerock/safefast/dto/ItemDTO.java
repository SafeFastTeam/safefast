package org.zerock.safefast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.safefast.entity.Item;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDTO {

    private String itemCode;
    private String itemName;
    private Integer width;
    private Integer length;
    private Integer height;
    private String material;
    private String blueprintOriginName;

    public ItemDTO(Item item) {
        this.itemCode = item.getItemCode();
        this.itemName = item.getItemName();
        this.width = item.getWidth();
        this.length = item.getLength();
        this.height = item.getHeight();
        this.material = item.getMaterial();
        this.blueprintOriginName = item.getBlueprintOriginName();
    }
}
