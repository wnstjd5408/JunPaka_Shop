package pakaCoding.flower.dto;

import lombok.Builder;
import lombok.Data;
import pakaCoding.flower.domain.entity.FileImage;
import pakaCoding.flower.domain.entity.Flower;

@Data
public class FileImageDto {


    private Long id;

    private String originFileName;

    private String savedFileName;

    private String uploadDir;   //경로명

    private String extension;   //확장자

    private Long size;          //파일사이즈

    private String contentType; //ContentType


    private Flower flower;

    private String repimgYn;

    public FileImageDto() {
    }

    @Builder
    public FileImageDto(Long id, String originFileName, String savedFileName, String uploadDir, String extension, Long size, String contentType, Flower flower, String repimgYn) {
        this.id = id;
        this.originFileName = originFileName;
        this.savedFileName = savedFileName;
        this.uploadDir = uploadDir;
        this.extension = extension;
        this.size = size;
        this.contentType = contentType;
        this.flower = flower;
        this.repimgYn = repimgYn;
    }

    public FileImage toEntity(){
        return FileImage.builder()
                .originFileImgName(originFileName)
                .savedFileImgName(savedFileName)
                .uploadDir(uploadDir)
                .extension(extension)
                .size(size)
                .contentType(contentType)
                .repimgYn(repimgYn)
                .flower(flower)
                .build();
    }
}
