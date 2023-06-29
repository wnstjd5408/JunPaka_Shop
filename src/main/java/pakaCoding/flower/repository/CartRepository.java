package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pakaCoding.flower.domain.entity.Cart;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c join fetch c.member m where m.userid = :userId")
    Cart findByMemberUserid(String userId);
}
