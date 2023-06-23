package pakaCoding.flower.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import pakaCoding.flower.domain.entity.BrandImage;
import pakaCoding.flower.domain.entity.Image;
import pakaCoding.flower.domain.entity.ItemImage;
import pakaCoding.flower.domain.entity.ReviewImage;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto {


    private Long id;

    private String originFileName;

    private String imgUrl;

    private String uploadDir;   //경로명

    private String extension;   //확장자

    private Long size;          //파일사이즈

    private String contentType; //ContentType

    @ColumnDefault("N")
    private String repImgYn;



    //DTO -> Entity

    public BrandImage toEntity(){
        return BrandImage.builder()
                .originFileImgName(originFileName)
                .savedFileImgName(imgUrl)
                .uploadDir(uploadDir)
                .extension(extension)
                .size(size)
                .contentType(contentType)
                .repImgYn(repImgYn)
                .build();
    }


    /* DTO -> Entity*/
    public ItemImage toEntityItemImage(){
        return ItemImage.builder()
                .originFileImgName(originFileName)
                .savedFileImgName(imgUrl)
                .uploadDir(uploadDir)
                .extension(extension)
                .size(size)
                .contentType(contentType)
                .repImgYn(repImgYn)
                .build();
    }

    /* DTO -> Entity*/
    public ReviewImage toEntityReviewImage() {
        return ReviewImage.builder()
                .originFileImgName(originFileName)
                .savedFileImgName(imgUrl)
                .uploadDir(uploadDir)
                .extension(extension)
                .size(size)
                .contentType(contentType)
                .repImgYn(repImgYn)
                .build();
    }

    /* Entity -> Dto */
    public ImageDto(Image image){
        this.id = image.getId();
        this.extension = image.getExtension();
        this.contentType = image.getContentType();
        this.repImgYn = image.getRepImgYn();
        this.originFileName = image.getOriginFileImgName();
        this.uploadDir = image.getUploadDir();
        this.size = image.getSize();
        this.imgUrl = image.getSavedFileImgName();
    }

}
