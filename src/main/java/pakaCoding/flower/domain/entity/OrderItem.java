package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pakaCoding.flower.domain.constant.ReviewStatus;

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
    @JoinColumn(name = "item_id")
    private Item item;


    @NotNull
    private int orderPrice; // 주문 가격

    @NotNull
    private int count; //주문 수량


    @Enumerated(EnumType.STRING)
    @NotNull
    private ReviewStatus reviewStatus; //리뷰여부


    //==조회 로직==//
    /** 주문상품 전체 가격 조회 */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    //== 생성 메서드 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();;
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(orderPrice);

        orderItem.changeReviewId(ReviewStatus.NO);
        item.removeStockQuantity(count);

        return orderItem;
    }

    //==Review 유무 바꾸기==//
    public void changeReviewId(ReviewStatus reviewStatus){
        if(reviewStatus == ReviewStatus.CANCEL){
            this.reviewStatus = ReviewStatus.CANCEL;
        }
        else if(reviewStatus == ReviewStatus.YES){
            this.reviewStatus = ReviewStatus.YES;
        }
        else{
            this.reviewStatus = ReviewStatus.NO;
        }
    }

    //==주문 취소==//
    public void cancel() {
        this.reviewStatus = ReviewStatus.CANCEL;
        this.getItem().addStockQuantity(count);
    }
}
