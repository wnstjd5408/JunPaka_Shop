package pakaCoding.flower.dto;

import lombok.Data;
import pakaCoding.flower.domain.entity.Brand;
import pakaCoding.flower.domain.entity.Item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class BrandItemListDto {

    //Item 관련
    private Long itemId;
    private String itemName;
    private String itemImgURL;
    private Integer price;


    //브랜드 관련
    private String brandName;
    private String detailBrandComment;

    public BrandItemListDto(Item item) {
        this.itemId = item.getId();
        this.itemName = item.getName();
        this.price = item.getPrice();
        this.brandName = item.getBrand().getName();
        this.detailBrandComment = item.getBrand().getBrandComment();
    }


    public void addItemImgUrl(String itemImgURL) {
        this.setItemImgURL(itemImgURL);
    }




}

