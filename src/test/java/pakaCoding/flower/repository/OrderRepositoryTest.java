package pakaCoding.flower.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.constant.DeliveryStatus;
import pakaCoding.flower.domain.constant.FlowerSellStatus;
import pakaCoding.flower.domain.constant.Gender;
import pakaCoding.flower.domain.constant.Role;
import pakaCoding.flower.domain.entity.*;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.yml")
@Slf4j
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    FlowerRepository flowerRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;


    private Flower registerFLower(String name, int price, int stockQuantity, Type type){
        return Flower.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .type(type)
                .flowerSellStatus(FlowerSellStatus.SELL)
                .hitCount(0L)
                .fileImages(null)
                .delYn("Y")
                .build();
    }


    @Test
    @DisplayName("CascadeTest 영속성 테스트")
    public void cascadeTest() {
        Type type1 = Type.builder()
                .count(0)
                .id(1)
                .typename("꽃바구니")
                .build();
        typeRepository.save(type1);

        Order order = new Order();

        for (int i = 0; i < 3; i++) {
            Flower flower = this.registerFLower("테스트" + i, 1000, 1, type1);
            flowerRepository.save(flower);

            Member member = new Member("wnstjd5408@naver.com", "12345", "테스트", Gender.MAN, LocalDate.now(), Role.ADMIN, new Address("가", "나", "다"));
            memberRepository.save(member);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            delivery.setDeliveryStatus(DeliveryStatus.READY);

            // 3. OrderItem 생성 및 초기화
            OrderItem orderItem = new OrderItem();
            orderItem.setFlower(flower);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);

            //4. Order에 OrderItem add(총 3개)
            order.getOrderItems().add(orderItem);
        }

        orderRepository.saveAndFlush(order);


        em.clear();


        List<OrderItem> savedOrderItem = orderItemRepository.findAll();
        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        log.info("savedOrder.getOrderItems().size() ={}", 3);
        Assertions.assertEquals(3, savedOrder.getOrderItems().size());

    }


}