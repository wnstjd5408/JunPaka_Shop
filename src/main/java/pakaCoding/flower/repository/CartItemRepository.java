package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pakaCoding.flower.domain.entity.CartItem;
import pakaCoding.flower.dto.CartListDto;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndFlowerId(Long cartId, Long flowerId);


    @Query("select new pakaCoding.flower.dto.CartListDto(ci.id, f.name, f.price, ci.count, fi.savedFileImgName)" +
            "from CartItem ci, FileImage fi " +
            "join ci.flower f " +
            "where ci.cart.id = :cartId " +
            "and fi.flower.id = ci.flower.id " +
            "and fi.repimgYn = 'Y' " +
            "order by fi.regDate desc ")
    List<CartListDto> findCartListDto(Long cartId);


    Integer countByCartId(Long cartId);
}
