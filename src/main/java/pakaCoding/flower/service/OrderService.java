package pakaCoding.flower.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.*;
import pakaCoding.flower.dto.OrderDto;
import pakaCoding.flower.dto.OrderItemDto;
import pakaCoding.flower.dto.OrderMyPageDto;
import pakaCoding.flower.repository.ItemImageRepository;
import pakaCoding.flower.repository.FlowerRepository;
import pakaCoding.flower.repository.MemberRepository;
import pakaCoding.flower.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemImageRepository fileImageRepository;
    private final FlowerRepository flowerRepository;


    @Transactional
    public Long order(OrderDto orderDto, String userid) {
        log.info("OrderService에서 order 실행");
        //엔티티 조회
        List<OrderItem> orderItemList = new ArrayList<>();
        Flower flower = flowerRepository.findById(orderDto.getFlowerId())
                .orElseThrow(EntityNotFoundException::new);
        orderItemList.add(OrderItem.createOrderItem(flower, flower.getPrice(), orderDto.getCount()));

        Member member = memberRepository.findByUserid(userid).orElseThrow(EntityNotFoundException::new);
        //배송정보 생성
        Delivery delivery = Delivery.createDelivery(member);


        //Order 객체 생성

        Order order = Order.createOrder(member, delivery, orderItemList);

        //Order 객체 Db 저장 (Cascade로 인해 OrderItem, Delivery 객체도 같이 저장)
        orderRepository.save(order);
        return order.getId();
    }

    public Long orders(List<OrderDto> orderDtoList, String userId) {

        //로그인한 유저 조회
        Member member = memberRepository.findByUserid(userId).get();

        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderDto orderDto : orderDtoList) {
            Flower flower = flowerRepository.findById(orderDto.getFlowerId()).orElseThrow(EntityNotFoundException::new);
            OrderItem orderItem = OrderItem.createOrderItem(flower, flower.getPrice(), orderDto.getCount());
            orderItemList.add(orderItem);
        }
        //Delivery 객체 생성
        Delivery delivery = Delivery.createDelivery(member);
        //Order Entity 클래스에 존재하는 createOrder 메소드로 Order 생성 및 저장
        Order order = Order.createOrder(member, delivery, orderItemList);
        orderRepository.save(order);
        return order.getId();
    }

    //주문 내역 조회
    @Transactional(readOnly = true)
    public Page<OrderMyPageDto> getOrderList(String userId, int page){
        Pageable pageable = PageRequest.of(page, 5);
        Page<Order> orders = orderRepository.findOrders(userId, pageable);

        List<OrderMyPageDto> orderMyPageDtos = orders.stream()
                .map(o -> {
                    OrderMyPageDto orderMyPageDto = new OrderMyPageDto(o);
                    List<OrderItem> orderItems = o.getOrderItems();
                    for (OrderItem orderItem : orderItems) {

                        Image image = fileImageRepository.findByFlowerIdAndRepImgYn(orderItem.getFlower().getId(), "Y");
                        OrderItemDto orderItemDto = new OrderItemDto(orderItem, image.getSavedFileImgName());
                        orderMyPageDto.addOrderItemDto(orderItemDto);
                    }
                    return orderMyPageDto;
                }).collect(Collectors.toList());

        log.info("주문의 수 : {}", orders.getTotalElements());
        return new PageImpl<>(orderMyPageDtos, pageable, orders.getTotalElements());
    }


    //주문 취소
    public void orderCancel(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.orderCancel();
    }
}
