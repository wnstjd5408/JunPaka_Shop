package pakaCoding.flower.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pakaCoding.flower.domain.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.member.userid = :userId " +
            "order by  o.createDate desc ")
    Page<Order> findOrders(@Param("userId") String userId, Pageable pageable);
}
