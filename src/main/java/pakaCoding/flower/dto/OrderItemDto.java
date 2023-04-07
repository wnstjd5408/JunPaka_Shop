package pakaCoding.flower.dto;

import lombok.Getter;
import lombok.Setter;
import pakaCoding.flower.domain.entity.OrderItem;

@Getter
@Setter
public class OrderItemDto {


    private String flowerName;
    private int count;
    private int orderPrice;
    private String imgUrl;

    public OrderItemDto(OrderItem orderItem, String imgUrl) {
        this.flowerName = orderItem.getFlower().getName();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }
}
