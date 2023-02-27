package pakaCoding.flower.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.TimeEntity;
import pakaCoding.flower.domain.entity.Type;

import java.util.List;

@Data
public class FlowerDto extends TimeEntity {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    private Type type;
    private Long hitCount;
    private List<MultipartFile> multipartFile;

    public FlowerDto() {

    }

    public FlowerDto(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;

    }

    @Builder
    public FlowerDto(Long id, String name, int price, int stockQuantity, Type type, Long hitCount, List<MultipartFile> multipartFile) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.type = type;
        this.hitCount = hitCount;
        this.multipartFile = multipartFile;
    }




    public Flower toEntity(){
        return Flower.builder()
                .type(type)
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .delYn("N")
                .hitCount(0L)
                .build();
    }
}
