package pakaCoding.flower.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import pakaCoding.flower.domain.entity.Review;

import java.util.List;

@Data
public class ReviewFormDto {


    private Long orderItemId;

    @NotNull(message = "별점을 필 수 입니다.")
    private Integer rating;


    @NotBlank(message = "리뷰는 필수 입력 값 입니다.")
    private String comment;

    private List<MultipartFile> multipartFile;




}
