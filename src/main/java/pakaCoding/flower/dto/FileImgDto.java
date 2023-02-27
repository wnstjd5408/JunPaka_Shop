package pakaCoding.flower.dto;

import lombok.Data;

@Data
public class FileImgDto {


    private Long id;

    private String originFileImgName;

    private String savedFileImgName;

    private String uploadDir;

    private String extension;


}
