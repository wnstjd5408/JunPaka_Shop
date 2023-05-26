package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pakaCoding.flower.domain.entity.CartItem;
import pakaCoding.flower.dto.CartListDto;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndFlowerId(Long cartId, Long flowerId);


    @Query("select new pakaCoding.flower.dto.CartListDto(ci.id, f.name, f.price, ci.count, i.savedFileImgName)" +
            "from CartItem ci, ItemImage i " +
            "join ci.flower f " +
            "where ci.cart.id = :cartId " +
            "and i.flower.id = ci.flower.id " +
            "and i.repImgYn = 'Y' " +
            "order by i.createDate desc ")
    List<CartListDto> findCartListDto(Long cartId);


    Integer countByCartId(Long cartId);
}
