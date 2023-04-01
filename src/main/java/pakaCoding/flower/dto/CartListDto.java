package pakaCoding.flower.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartListDto {

    private Long cartItemId;
    private String flowerName;
    private int price;
    private int count;
    private String imgUrl;


    @Builder
    public CartListDto(Long cartItemId, String flowerName, int price, int count, String imgUrl) {
        this.cartItemId = cartItemId;
        this.flowerName = flowerName;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}
