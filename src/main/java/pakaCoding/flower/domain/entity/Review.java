package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import pakaCoding.flower.domain.constant.ReviewStatus;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="reviews")
public class Review extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;


    @Column(columnDefinition = "TEXT")
    @NotNull
    private String comment;

    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="flower_id") //외래키
    private Flower flower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")//외래키
    private Member member;


    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST)
    private List<ReviewImage> reviewImages = new ArrayList<>();



    public void addReviewFiles(List<ReviewImage> reviewImages){
        this.reviewImages = reviewImages;
        reviewImages.forEach(reviewImage -> reviewImage.setReview(this));
    }
    //==생성 메서드==//
    public static Review createReview(Member member, Flower flower, OrderItem orderItem, String comment, int rating){

        Review review = new Review();
        review.setComment(comment);
        review.setRating(rating);
        review.setFlower(flower);
        review.setMember(member);

        orderItem.changeReviewId(ReviewStatus.YES);
        return review;
    }
}
