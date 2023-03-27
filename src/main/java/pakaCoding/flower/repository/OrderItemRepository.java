package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pakaCoding.flower.domain.entity.OrderItem;

// 테스트용
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
