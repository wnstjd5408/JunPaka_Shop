package pakaCoding.flower.dto;


import lombok.Builder;
import lombok.Data;
import pakaCoding.flower.domain.entity.BoardFile;
import pakaCoding.flower.domain.entity.File;

@Data
public class BoardFileDto {

    private Long id;

    private Long flowerId;


    public BoardFileDto() {
    }

    @Builder
    public BoardFileDto(Long flowerId) {
        flowerId = flowerId;
    }

    //entity로 바꿔줄때 file 받는 부분을 추가
    public BoardFile toEntity(File file){
        return BoardFile.builder()
                .flowerId(flowerId)
                .file(file)
                .build();
    }
}


