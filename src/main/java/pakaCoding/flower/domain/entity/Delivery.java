package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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


}