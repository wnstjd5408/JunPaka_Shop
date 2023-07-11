package pakaCoding.flower.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.constant.*;
import pakaCoding.flower.domain.entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@Slf4j
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;


    private Item registerItem(String name, int price, int stockQuantity, Type type){
        return Item.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .type(type)
                .itemSellStatus(ItemSellStatus.SELL)
                .hitCount(0L)
                .itemImages(null)
                .delYn("Y")
                .build();
    }


    @Test
    @DisplayName("CascadeTest 영속성 테스트")
    public void cascadeTest() {
        Type type1 = getType();
        Member member = getMember();
        Order order = new Order();

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setDeliveryStatus(DeliveryStatus.READY);
        order.setDelivery(delivery);

        for (int i = 0; i < 3; i++) {
            Item item = this.registerItem("테스트" + i, 1000, 1, type1);
            itemRepository.save(item);

            // 3. OrderItem 생성 및 초기화
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
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
    @Test
    public void cancelOrderTest(){
        Type type1 = getType();
        Member member = getMember();

        Delivery delivery = Delivery.createDelivery(member);

        Item item = this.registerItem("테스트", 1000, 11, type1);
        itemRepository.save(item);
        List<OrderItem> orderItemList = new ArrayList<>();
        // 3. OrderItem 생성 및 초기화
        OrderItem orderItem = OrderItem.createOrderItem(item,10000, 10);
        orderItemList.add(orderItem);


        Order order = Order.createOrder(member, delivery, orderItemList);
        //4. Order에 OrderItem add(총 3개)

        Order order1 = orderRepository.saveAndFlush(order);
        log.info("findOrders 쿼리 실행");
        Page<Order> orders = orderRepository.findOrders(member.getUserid(), PageRequest.of(0, 5));

        Order findOrder = orderRepository.findById(order1.getId()).orElseThrow(EntityNotFoundException::new);
        findOrder.orderCancel();


        //then
        log.info("배송 전송상태  :  {}", findOrder.getDelivery().getDeliveryStatus());
        assertThat(DeliveryStatus.CANCEL).isEqualTo(findOrder.getDelivery().getDeliveryStatus());
        assertThat(OrderStatus.CANCEL).isEqualTo(findOrder.getOrderStatus());
        assertThat(11).isEqualTo(item.getStockQuantity());



    }


    private Member getMember() {
        Member member = new Member("wnstjd5408@naver.com", "12345", "admin", "테스트",
                Gender.MAN, LocalDate.now(), Role.ADMIN, new Address("가", "나", "다"), "010-1234-5678");
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

    public Order createOrder(){
        Order order = new Order();
        for(int i = 0; i<3; i++){
            Item item = registerItem("테스트" + i, 1000, 1, getType());


            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        order.setMember(getMember());
        orderRepository.save(order);
        return order;
    }

    @Test
    @DisplayName("즉시 로딩 테스트")
    public void eagerLoadingTest(){
        Order order = this.createOrder();
        Long orderItem_id = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();
        OrderItem orderItem = orderItemRepository.findById(orderItem_id)
                .orElseThrow(EntityNotFoundException::new);
        log.info("orderItem ={}", orderItem.getClass());
    }

    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest(){
        Order order = this.createOrder();
        Long orderItem_id = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItem_id).orElseThrow(EntityNotFoundException::new);
        System.out.println("Order class : " + orderItem.getOrder().getClass());
        System.out.println("-------------------------------------------------");
        System.out.println("orderItem.getOrder().getCreateDate() = " + orderItem.getOrder().getCreateDate());
        System.out.println("-------------------------------------------------");

    }





}