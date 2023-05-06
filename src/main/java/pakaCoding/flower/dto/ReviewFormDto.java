package pakaCoding.flower.dto;

import lombok.Data;
import pakaCoding.flower.domain.entity.Review;

@Data
public class ReviewFormDto {


    private Long orderItemId;


    private Integer rating;


    private String comment;

}
