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
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;



    @Column(columnDefinition = "TEXT")
    @NotNull
    private String comment;


    @Column(name = "created_date", updatable = false)
    @CreatedDate
    private String createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private String modifiedDate;

    @ManyToOne
    @JoinColumn(name="flower_id")
    private Flower flower;

    @ManyToOne
    @JoinColumn(name ="member_id")
    private Member member;
}
