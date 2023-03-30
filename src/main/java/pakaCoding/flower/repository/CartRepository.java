package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pakaCoding.flower.domain.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long memberId);
}
