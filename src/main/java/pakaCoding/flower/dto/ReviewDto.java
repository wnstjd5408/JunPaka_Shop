package pakaCoding.flower.dto;

import lombok.Getter;
import lombok.Setter;
import pakaCoding.flower.domain.entity.Review;
import pakaCoding.flower.domain.entity.ReviewImage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReviewDto {

    private Long reviewId;

    private Integer rating;

    private String comment;

    private String createReviewDate;

    private String username;

    private List<String> imgUrlList = new ArrayList<>();

    public ReviewDto(Review review) {
        this.reviewId = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.createReviewDate = review.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.username = review.getMember().getUsername();
    }

    public void addImgUrlList(String imgUrl){
        imgUrlList.add(imgUrl);
    }
}
