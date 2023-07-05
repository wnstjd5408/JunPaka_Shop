package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pakaCoding.flower.domain.entity.CartItem;
import pakaCoding.flower.dto.CartListDto;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);


    @Query("select new pakaCoding.flower.dto.CartListDto(ci.id, i.name, i.price, ci.count, ii.savedFileImgName)" +
            "from CartItem ci, ItemImage ii " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and ii.item.id = ci.item.id " +
            "and ii.repImgYn = 'Y' " +
            "and i.delYn = 'N' " +
            "order by ii.createDate desc ")
    List<CartListDto> findCartListDto(Long cartId);


    @Query(value = "select ci from CartItem ci " +
            "join fetch ci.item i " +
            "join i.itemImages ii " +
            "where ci.cart.id = :cartId " +
            "and i.delYn = 'N' " +
            "and ii.repImgYn  = 'Y'" +
            "order by i.name desc ")
    List<CartItem> findCartItems(Long cartId);

    Integer countByCartId(Long cartId);
}
