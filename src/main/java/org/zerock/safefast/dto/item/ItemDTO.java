package org.zerock.safefast.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String blueprintSaveName;

}
