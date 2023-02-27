package pakaCoding.flower.dto;

import lombok.Builder;
import lombok.Data;
import pakaCoding.flower.domain.entity.FileImage;

@Data
public class FileImgDto {


    private Long id;

    private String originFileImgName;

    private String savedFileImgName;

    private String uploadDir;

    private String extension;

    private Long size;          //파일사이즈

    private String contentType; //ContentType


    private String repimgYn;  //대표 이미지 여부

    @Builder
    public FileImgDto(Long id, String originFileImgName, String savedFileImgName, String uploadDir, String extension,
                      Long size, String contentType, String repimgYn) {
        this.id = id;
        this.originFileImgName = originFileImgName;
        this.savedFileImgName = savedFileImgName;
        this.uploadDir = uploadDir;
        this.extension = extension;
        this.size = size;
        this.contentType = contentType;
        this.repimgYn = repimgYn;
    }

    public FileImage toEntity(){
        return FileImage.builder()
                .originFileImgName(originFileImgName)
                .savedFileImgName(savedFileImgName)
                .uploadDir(uploadDir)
                .extension(extension)
                .size(size)
                .contentType(contentType)
                .repimgYn(repimgYn)
                .build();
    }

}
