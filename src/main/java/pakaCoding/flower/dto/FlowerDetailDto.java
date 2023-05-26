package pakaCoding.flower.dto;

import lombok.Data;
import pakaCoding.flower.domain.constant.FlowerSellStatus;
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
    private List<String> imgUrlList;


    /* Entity -> Dto */
    public FlowerDetailDto(Flower flower, List<String> imgUrlList){
        this.id = flower.getId();
        this.name = flower.getName();
        this.price = flower.getPrice();
        this.stockQuantity = flower.getStockQuantity();
        this.detailComment =flower.getDetailComment();
        this.flowerSellStatus = flower.getFlowerSellStatus();
        this.imgUrlList = imgUrlList;
    }
}
