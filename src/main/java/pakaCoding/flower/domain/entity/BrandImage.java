package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("B")
public class BrandImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Builder
    public BrandImage(Long id, String originFileImgName, String savedFileImgName, String uploadDir, String extension, Long size, String contentType, String repImgYn, Brand brand) {
        super(id, originFileImgName, savedFileImgName, uploadDir, extension, size, contentType, repImgYn);
    }
}
