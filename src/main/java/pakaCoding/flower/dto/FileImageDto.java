package pakaCoding.flower.dto;

import lombok.Builder;
import lombok.Data;
import pakaCoding.flower.domain.entity.FileImage;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.ReviewImage;

@Data
public class FileImageDto {


    private Long id;

    private String originFileName;

    private String imgUrl;

    private String uploadDir;   //경로명

    private String extension;   //확장자

    private Long size;          //파일사이즈

    private String contentType; //ContentType

    private String repimgYn;

    public FileImageDto() {
    }

    @Builder
    public FileImageDto(Long id, String originFileName, String savedFileName, String uploadDir, String extension, Long size, String contentType, String repimgYn) {
        this.id = id;
        this.originFileName = originFileName;
        this.imgUrl = savedFileName;
        this.uploadDir = uploadDir;
        this.extension = extension;
        this.size = size;
        this.contentType = contentType;
        this.repimgYn = repimgYn;
    }

    /* DTO -> Entity*/
    public FileImage toEntity(){
        return FileImage.builder()
                .originFileImgName(originFileName)
                .savedFileImgName(imgUrl)
                .uploadDir(uploadDir)
                .extension(extension)
                .size(size)
                .contentType(contentType)
                .repimgYn(repimgYn)
                .build();
    }

    /* DTO -> Entiyu*/
    public ReviewImage toEntityReviewImage() {
        return ReviewImage.builder()
                .originFileImgName(originFileName)
                .savedFileImgName(imgUrl)
                .uploadDir(uploadDir)
                .extension(extension)
                .size(size)
                .contentType(contentType)
                .repimgYn(repimgYn)
                .build();
    }

    /* Entity -> Dto */
    public FileImageDto(FileImage fileImage){
        this.imgUrl = fileImage.getSavedFileImgName();
    }

}
