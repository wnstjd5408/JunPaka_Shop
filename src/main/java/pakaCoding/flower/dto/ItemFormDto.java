package pakaCoding.flower.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;
import pakaCoding.flower.domain.constant.ItemSellStatus;
import pakaCoding.flower.domain.entity.Brand;
import pakaCoding.flower.domain.entity.Item;
import pakaCoding.flower.domain.entity.TimeEntity;
import pakaCoding.flower.domain.entity.Type;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ItemFormDto extends TimeEntity {

    private Long id;

    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String name;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockQuantity;

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String detailComment;

    @NotNull(message = "타입 선택은 필수 입력 값입니다.")
    private Type type;

    @NotNull(message = "브랜드 선택은 필수 입력 값입니다.")
    private Brand brand;

    private ItemSellStatus itemSellStatus;


    // 이미지 사용
    private MultipartFile thumbnails;
    private List<MultipartFile> multipartFile;

    //상품 수정 시 사용되는 멤버 변수들
    private List<ImageDto> imageDtolist = new ArrayList<>();
    private List<Long> itemImgIds = new ArrayList<>();

    public ItemFormDto() {
    }

    public ItemFormDto(Item item, List<ImageDto> imageDtolist) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.detailComment = item.getDetailComment();
        this.type = item.getType();
        this.brand = item.getBrand();
        this.itemSellStatus = item.getItemSellStatus();
        this.imageDtolist = imageDtolist;
    }

    /**
     * 파일저장때 사용
     */
    //DTO -> Item
    public Item toEntity(){
        return Item.builder()
                .brand(brand)
                .type(type)
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .detailComment(detailComment)
                .itemSellStatus(itemSellStatus)
                .delYn("N")
                .hitCount(0L)
                .build();
    }


}
