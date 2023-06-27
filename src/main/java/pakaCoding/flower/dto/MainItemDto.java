package pakaCoding.flower.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MainItemDto {


    private Long id;
    private String itemName;
    private String imgURL;
    private Integer price;

    @Builder
    public MainItemDto(Long id, String itemName, String imgURL, Integer price) {
        this.id = id;
        this.itemName = itemName;
        this.imgURL = imgURL;
        this.price = price;
    }


    public void addImgUrl(String imgURL){
        this.setImgURL(imgURL);
    }
}
