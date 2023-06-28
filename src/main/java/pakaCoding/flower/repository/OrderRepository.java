package pakaCoding.flower.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pakaCoding.flower.domain.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query(value = "select o from Order o" +
            " join fetch o.member " +
            " join o.orderItems oi" +
            " where o.member.userid= :userId" +
            " order by o.createDate desc",
    countQuery = "select count(o) from Order o where o.member.userid = :userId")
    Page<Order> findOrders(String userId, Pageable pageable);


    @Query(value = "select o from Order o " +
            " join fetch  o.member " +
            " join o.orderItems oi " +
            " where oi.id = :orderItemId")
    Order findCheck(Long orderItemId);
}
