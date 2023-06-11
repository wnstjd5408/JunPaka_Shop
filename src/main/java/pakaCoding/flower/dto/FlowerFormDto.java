package pakaCoding.flower.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;
import pakaCoding.flower.domain.constant.FlowerSellStatus;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.TimeEntity;
import pakaCoding.flower.domain.entity.Type;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class FlowerFormDto extends TimeEntity {

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

    private FlowerSellStatus flowerSellStatus;


    // 이미지 사용
    private MultipartFile thumbnails;
    private List<MultipartFile> multipartFile;

    //상품 수정 시 사용되는 멤버 변수들
    private List<ImageDto> imageDtolist = new ArrayList<>();
    private List<Long> itemImgIds = new ArrayList<>();

    public FlowerFormDto() {
    }

    public FlowerFormDto(Flower flower, List<ImageDto> imageDtolist) {
        this.id = flower.getId();
        this.name = flower.getName();
        this.price = flower.getPrice();
        this.stockQuantity = flower.getStockQuantity();
        this.detailComment = flower.getDetailComment();
        this.type = flower.getType();
        this.flowerSellStatus = flower.getFlowerSellStatus();
        this.imageDtolist = imageDtolist;
    }

    /**
     * 파일저장때 사용
     */
    //DTO -> Flower
    public Flower toEntity(){
        return Flower.builder()
                .type(type)
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .detailComment(detailComment)
                .flowerSellStatus(flowerSellStatus)
                .delYn("N")
                .hitCount(0L)
                .build();
    }


}
