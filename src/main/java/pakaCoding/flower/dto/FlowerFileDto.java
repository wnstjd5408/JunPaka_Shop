package pakaCoding.flower.dto;


import lombok.Builder;
import lombok.Data;
import pakaCoding.flower.domain.entity.FlowerFile;
import pakaCoding.flower.domain.entity.File;

@Data
public class FlowerFileDto {

    private Long id;

    private Long flowerId;


    public FlowerFileDto() {
    }

    @Builder
    public FlowerFileDto(Long flowerId) {
        this.flowerId = flowerId;
    }

    //entity로 바꿔줄때 file 받는 부분을 추가
    public FlowerFile toEntity(File file){
        return FlowerFile.builder()
                .flowerId(flowerId)
                .file(file)
                .build();
    }
}


