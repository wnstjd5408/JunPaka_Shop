package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name="reviews")
public class Review extends TimeEntity{

    @Id
    @GeneratedValue
    private Long id;


    @Column(columnDefinition = "TEXT")
    @NotNull
    private String comment;

    private int rating;

    @ManyToOne
    @JoinColumn(name="flower_id") //외래키
    private Flower flower;

    @ManyToOne
    @JoinColumn(name ="member_id")//외래키
    private Member member;
}
