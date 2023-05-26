package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("R")
public class ReviewImage extends Image {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public ReviewImage(Long id, String originFileImgName, String savedFileImgName, String uploadDir, String extension,
                 Long size, String contentType, String repImgYn) {
            super(id,originFileImgName,savedFileImgName,uploadDir,extension,size,contentType,repImgYn);
    }


}
