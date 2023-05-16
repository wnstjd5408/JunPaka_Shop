package pakaCoding.flower.dto;

import pakaCoding.flower.domain.entity.Review;

import java.time.format.DateTimeFormatter;

public class ReviewDto {

    private Long reviewId;

    private Integer rating;

    private String comment;

    private String createReviewDate;

    public ReviewDto(Review review) {
        this.reviewId = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.createReviewDate = review.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
