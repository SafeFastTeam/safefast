package org.zerock.safefast.dto.inventory;

import org.zerock.safefast.entity.Item; // Item 클래스 import 추가
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItemDTO {

    private Long id;
    private String itemCode;
    private String itemName;
    private Integer width;
    private Integer length;
    private Integer height;
    private String material;
    private int quantityAvailable;
    private int receiveQuantity;
    private int releaseQuantity;
    private double price; // 계약 정보의 단가

    private Item item;

    // Item 객체를 설정하는 메서드
    public void setItem(Item item) {
        this.item = item;
    }

    // setter 메서드 추가
    public void setReceiveQuantity(int receiveQuantity) {
        // 해당 필드 값을 설정하는 메서드를 추가합니다.
        this.receiveQuantity = receiveQuantity;
    }

    // setter 메서드 추가
    public void setReleaseQuantity(int releaseQuantity) {
        // 해당 필드 값을 설정하는 메서드를 추가합니다.
        this.releaseQuantity = releaseQuantity;
    }

    // setter 메서드 추가
    public void setPrice(double price) {
        // 해당 필드 값을 설정하는 메서드를 추가합니다.
        this.price = price;
    }

}
