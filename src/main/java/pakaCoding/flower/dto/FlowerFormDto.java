package pakaCoding.flower.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String detailComment;

    @NotNull(message = "타입 선택은 필수 입력 값입니다.")
    private Type type;
    private MultipartFile thumbnails;
    private List<MultipartFile> multipartFile;
    private FlowerSellStatus flowerSellStatus;

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
