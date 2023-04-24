package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import pakaCoding.flower.domain.constant.DeliveryStatus;
import pakaCoding.flower.domain.constant.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //주문 회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery; //배송정보

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }


    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //OrderItem 한개 씩 추가
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //== 생성 메서드 ==//
    public static Order createOrder(Member member, Delivery delivery, List<OrderItem> orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==조회 로직==//
    /* 전체 주문 가격 조회 */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }


    //==취소 로직==//
    /* 주문 취소 */
    public void orderCancel(){

        this.orderStatus = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

}
