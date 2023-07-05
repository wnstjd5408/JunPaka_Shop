package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
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

    @Column(nullable = false)
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id") //외래키
    private Item item;

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
    public static Review createReview(Member member, Item item, OrderItem orderItem, String comment, int rating){

        Review review = new Review();
        review.setComment(comment);
        review.setRating(rating);
        review.setItem(item);
        review.setMember(member);

        orderItem.changeReviewId(ReviewStatus.YES);
        return review;
    }
}
