package pakaCoding.flower.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import pakaCoding.flower.domain.entity.Brand;

import java.util.List;

@Data
@RequiredArgsConstructor
public class BrandFormDto {


    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String brandName;

    @NotBlank(message = "브랜드 상세정보 값을 필수 입력 값 입니다.")
    private String detailBrandComment;


    private List<MultipartFile> brandImages;


    //DTO -> Brand
    public Brand toEntity(){
        return Brand.builder()
                .brandComment(detailBrandComment)
                .name(brandName)
                .build();
    }


}
