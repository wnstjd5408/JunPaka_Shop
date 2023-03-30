package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pakaCoding.flower.domain.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndFlowerId(Long cartId, Long flowerId);
}
