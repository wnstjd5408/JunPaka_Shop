package pakaCoding.flower.dto;

import lombok.Builder;
import lombok.Data;
import pakaCoding.flower.domain.entity.UploadFile;

@Data
public class FileDto {


    private Long id;

    private String originFileName;

    private String savedFileName;

    private String uploadDir;   //경로명

    private String extension;   //확장자

    private Long size;          //파일사이즈

    private String contentType; //ContentType


    public FileDto() {
    }

    @Builder
    public FileDto(Long id, String originFileName, String savedFileName, String uploadDir, String extension, Long size, String contentType) {
        this.id = id;
        this.originFileName = originFileName;
        this.savedFileName = savedFileName;
        this.uploadDir = uploadDir;
        this.extension = extension;
        this.size = size;
        this.contentType = contentType;
    }

    public UploadFile toEntity(){
        return UploadFile.builder()
                .originFileName(originFileName)
                .savedFileName(savedFileName)
                .uploadDir(uploadDir)
                .extension(extension)
                .size(size)
                .contentType(contentType)
                .build();
    }
}
