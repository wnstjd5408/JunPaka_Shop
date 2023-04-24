package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pakaCoding.flower.domain.constant.DeliveryStatus;

@Entity
@Getter
@Setter
public class Delivery {


    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;


    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;


    @Embedded
    private Address address;


    @Enumerated(EnumType.STRING)  //타입은 반드시 String으로!
    private DeliveryStatus deliveryStatus; //ENUM[READY(준비), COMP(배송)]

    //== 생성 메서드 == //
    public static Delivery createDelivery(Member member) {
        Delivery delivery = new Delivery();
        delivery.setDeliveryStatus(DeliveryStatus.READY);
        delivery.setAddress(member.getAddress());

        return delivery;

    }
}
