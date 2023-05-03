package pakaCoding.flower.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pakaCoding.flower.domain.constant.OrderStatus;
import pakaCoding.flower.domain.entity.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderMyPageDto {

    private Long orderId; //주문 취소에 이용
    private OrderStatus orderStatus;
    private String orderDate;
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();


    public OrderMyPageDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }


    public void addOrderItemDto(OrderItemDto orderItemDto) {
        orderItemDtoList.add(orderItemDto);
    }

}


