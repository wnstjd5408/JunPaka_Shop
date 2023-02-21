package pakaCoding.flower.dto;


import lombok.Builder;
import lombok.Data;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.FlowerFile;
import pakaCoding.flower.domain.entity.File;

@Data
public class FlowerFileDto {

    private Long id;

    private Flower flower;


    public FlowerFileDto() {
    }

    @Builder
    public FlowerFileDto(Flower flower) {
        this.flower = flower;
    }




    //entity로 바꿔줄때 file 받는 부분을 추가
    public FlowerFile toEntity(File file){
        return FlowerFile.builder()
                .flower(flower)
                .file(file)
                .build();
    }
}


