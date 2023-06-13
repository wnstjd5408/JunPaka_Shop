package pakaCoding.flower.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class AdminItemListDto {


    private Long id;
    private String itemName;
    private Integer price;
    private Integer stockQuantity;


    @Builder
    public AdminItemListDto(Long id, String itemName, Integer price, Integer stockQuantity) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

}
