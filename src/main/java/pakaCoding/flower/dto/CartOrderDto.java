package pakaCoding.flower.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDto {

    private Long cartItemId;
    private List<CartOrderDto> cartOrderDtoList;
}
