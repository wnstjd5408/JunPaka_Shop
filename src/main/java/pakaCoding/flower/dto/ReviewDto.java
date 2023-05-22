package pakaCoding.flower.dto;

import lombok.Getter;
import lombok.Setter;
import pakaCoding.flower.domain.entity.Review;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ReviewDto {

    private Long reviewId;

    private Integer rating;

    private String comment;

    private String createReviewDate;

    private String username;

    public ReviewDto(Review review) {
        this.reviewId = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.createReviewDate = review.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.username = review.getMember().getUsername();
    }
}
