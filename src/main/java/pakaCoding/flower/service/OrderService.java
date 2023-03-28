package pakaCoding.flower.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.constant.DeliveryStatus;
import pakaCoding.flower.domain.entity.*;
import pakaCoding.flower.dto.OrderDto;
import pakaCoding.flower.repository.FileImageRepository;
import pakaCoding.flower.repository.FlowerRepository;
import pakaCoding.flower.repository.MemberRepository;
import pakaCoding.flower.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final FileImageRepository fileImageRepository;
    private final FlowerRepository flowerRepository;


    @Transactional
    public Long order(OrderDto orderDto, String userid) {
        log.info("OrderService에서 order 실행");
        //엔티티 조회
        List<OrderItem> orderItemList = new ArrayList<>();
        Flower flower = flowerRepository.findById(orderDto.getFlowerId())
                .orElseThrow(EntityNotFoundException::new);
        OrderItem orderItem = OrderItem.createOrderItem(flower, flower.getPrice(), orderDto.getCount());
        orderItemList.add(orderItem);

        //배송정보 생성
        Member member = memberRepository.findByUserid(userid).get();

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setDeliveryStatus(DeliveryStatus.READY);


        //Order 객체 생성

        Order order = Order.createOrder(member, delivery, orderItemList);

        //Order 객체 Db 저장 (Cascade로 인해 OrderItem, Delivery 객체도 같이 저장)
        orderRepository.save(order);
        return order.getId();
    }
}
