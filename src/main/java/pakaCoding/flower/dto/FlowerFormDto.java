package pakaCoding.flower.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import pakaCoding.flower.domain.constant.ItemSellStatus;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.TimeEntity;
import pakaCoding.flower.domain.entity.Type;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
public class FlowerFormDto extends TimeEntity {

    private Long id;


    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String name;
    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;
    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockQuantity;
    private Type type;


    // 꽃 수정 시 사용되는 멤버 변수들
    private List<FileImgDto> fileImgDtoList = new ArrayList<>();
    private List<Long> flowerImgIds = new ArrayList<>();

    public FlowerFormDto() {

    }

    public FlowerFormDto(String name, Integer price, Integer stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;

    }

    @Builder
    public FlowerFormDto(Long id, String name, Integer price, Integer stockQuantity, Type type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.type = type;

    }



    //DTO => Entity
    public Flower toEntity(){
        return Flower.builder()
                .type(type)
                .name(name)
                .price(price.intValue())
                .stockQuantity(stockQuantity.intValue())
                .itemSellStatus(ItemSellStatus.SELL)
                .hitCount(0L)
                .delYn("N")
                .build();
    }
}
