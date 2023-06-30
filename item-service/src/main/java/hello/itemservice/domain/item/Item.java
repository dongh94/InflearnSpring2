package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Item {
    private Long id;
    private String itemName;
    // null 값이 더 합리적일때 사용. 0이 가격이라고 할 수 있으니 참고
    private Integer price;
    private Integer quantity;

    public Item() {

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
