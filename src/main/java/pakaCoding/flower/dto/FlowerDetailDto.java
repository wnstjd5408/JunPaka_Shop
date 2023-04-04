package pakaCoding.flower.dto;

import lombok.Data;
import pakaCoding.flower.domain.constant.FlowerSellStatus;
import pakaCoding.flower.domain.entity.FileImage;
import pakaCoding.flower.domain.entity.Flower;

import java.util.List;

@Data
public class FlowerDetailDto {

    private Long id;
    private String name;
    private Integer price;
    private Integer stockQuantity;
    private String detailComment;

    private FlowerSellStatus flowerSellStatus;
    private List<FileImageDto> fileImageDtoList;


    /* Entity -> Dto */
    public FlowerDetailDto(Flower flower){
        this.id = flower.getId();
        this.name = flower.getName();
        this.price = flower.getPrice();
        this.stockQuantity = flower.getStockQuantity();
        this.detailComment =flower.getDetailComment();
        this.flowerSellStatus = flower.getFlowerSellStatus();
    }
}
