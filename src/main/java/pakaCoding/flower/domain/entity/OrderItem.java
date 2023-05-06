package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

@Entity
@Table(name = "order_item")
@Getter @Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flower_id")
    private Flower flower;


    @NotNull
    private int orderPrice; // 주문 가격

    @NotNull
    private int count; //주문 수량


    private String reviewYn; //리뷰여부


    //==조회 로직==//
    /** 주문상품 전체 가격 조회 */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    //== 생성 메서드 ==//
    public static OrderItem createOrderItem(Flower flower, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();;
        orderItem.setFlower(flower);
        orderItem.setCount(count);
        orderItem.setOrderPrice(orderPrice);

        orderItem.setReviewYn("N");
        flower.removeStockQuantity(count);

        return orderItem;
    }

    //==Review 유무 바꾸기==//
    public void changeReviewId(){
        this.setReviewYn("Y");
    }

    //==주문 취소==//
    public void cancel() {
        this.getFlower().addStockQuantity(count);
    }
}
