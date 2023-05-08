package pakaCoding.flower.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Entity;
import pakaCoding.flower.domain.constant.FlowerSellStatus;
import pakaCoding.flower.domain.constant.Gender;
import pakaCoding.flower.domain.constant.OrderStatus;
import pakaCoding.flower.domain.constant.Role;
import pakaCoding.flower.domain.entity.*;
import pakaCoding.flower.dto.OrderDto;
import pakaCoding.flower.repository.FlowerRepository;
import pakaCoding.flower.repository.MemberRepository;
import pakaCoding.flower.repository.OrderRepository;
import pakaCoding.flower.repository.TypeRepository;
import pakaCoding.flower.service.OrderService;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
@Slf4j
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    FlowerRepository flowerRepository;

    @Autowired
    MemberRepository memberRepository;


    @Autowired
    TypeRepository typeRepository;

    private Flower registerFLower(String name, int price, int stockQuantity, Type type){
        return Flower.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .detailComment("테스트 설명")
                .flowerSellStatus(FlowerSellStatus.SELL)
                .hitCount(0L)
                .fileImages(null)
                .delYn("Y")
                .build();
    }
    @Test
    @DisplayName("주문 취소 테스트")
    public void cancelOrder() {
        //given
        Flower flower = registerFLower("테스트" , 1000, 11, getType());
        Flower saveFlower = flowerRepository.save(flower);
        Member member = getMember();

        //상품 상세 페이지 화면에서 넘어오는 값들 설정

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setFlowerId(saveFlower.getId());

        //주문 객체 DB에 저장
        Long orderId = orderService.order(orderDto, member.getUserid());


        //when
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        orderService.orderCancel(orderId);

        //then
        assertThat(OrderStatus.CANCEL).isEqualTo(order.getOrderStatus());
        assertThat(11).isEqualTo(flower.getStockQuantity());
    }
    @DisplayName("주문자 테스트")
    @Test
    void create(){
        //given
        Flower flower = registerFLower("테스트" , 1000, 11, getType());
        log.info("flower.getId() = {}", flower.getId());
        Flower saveFlower = flowerRepository.save(flower);
        log.info("saveFlower.getId() = {}", saveFlower.getId());

        Member member = getMember();

        //상품 상세 페이지 화면에서 넘어오는 값들 설정

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setFlowerId(saveFlower.getId());

        //주문 객체 DB에 저장
        Long orderId = orderService.order(orderDto, member.getUserid());
        log.info("orderId = {}", orderId);

        //저장된 주문 객체 조회
        Order order = orderRepository.findById(orderId).orElseThrow(RuntimeException::new);

        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = orderDto.getCount() * saveFlower.getPrice();

        log.info("order.getCreateDate() = {}", order.getCreateDate());
        log.info("order.getCreatedBy() = {}", order.getCreatedBy());


        //when
        assertThat(order).isNotNull();

        assertThat(totalPrice).isEqualTo(order.getTotalPrice());
    }


    private Member getMember() {
        Member member = new Member("wnstjd5408@naver.com", "12345", "test", "테스트", Gender.MAN, LocalDate.now(), Role.ADMIN, new Address("가", "나", "다"));
        memberRepository.save(member);
        return member;
    }
    private Type getType() {
        Type type1 = Type.builder()
                .count(0)
                .id(1)
                .typename("꽃바구니")
                .build();
        typeRepository.save(type1);
        return type1;
    }


}
