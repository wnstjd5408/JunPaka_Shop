package pakaCoding.flower.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import pakaCoding.flower.domain.constant.FlowerSellStatus;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.TimeEntity;
import pakaCoding.flower.domain.entity.Type;

import java.util.List;

@Data
public class FlowerFormDto extends TimeEntity {

    private Long id;

    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String name;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockQuantity;
    private Type type;
    private Long hitCount;
    private MultipartFile thumbnails;
    private List<MultipartFile> multipartFile;
    private FlowerSellStatus flowerSellStatus;


    public FlowerFormDto() {

    }


    @Builder
    public FlowerFormDto(Long id, String name, Integer price, Integer stockQuantity, Type type, Long hitCount,
                         MultipartFile thumbnails , List<MultipartFile> multipartFile, FlowerSellStatus flowerSellStatus) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.type = type;
        this.hitCount = hitCount;
        this.thumbnails =thumbnails;
        this.multipartFile = multipartFile;
        this.flowerSellStatus =flowerSellStatus;
    }





    public Flower toEntity(){
        return Flower.builder()
                .type(type)
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .flowerSellStatus(flowerSellStatus)
                .delYn("N")
                .hitCount(0L)
                .build();
    }
}
