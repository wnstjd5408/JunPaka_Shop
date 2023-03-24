package pakaCoding.flower.dto;

import lombok.Builder;
import lombok.Data;
import pakaCoding.flower.domain.entity.FileImage;

@Data
public class MainFlowerDto {


    private Long id;
    private String flowerName;
    private String imgURL;
    private Integer price;

    @Builder
    public MainFlowerDto(Long id, String flowerName, String imgURL, Integer price) {
        this.id = id;
        this.flowerName = flowerName;
        this.imgURL = imgURL;
        this.price = price;
    }
}
