package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pakaCoding.flower.domain.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
