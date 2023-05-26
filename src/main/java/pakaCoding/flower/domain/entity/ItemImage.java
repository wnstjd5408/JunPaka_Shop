package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("I")
public class ItemImage extends  Image{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flower_id")
    private Flower flower;


    @Builder
    public ItemImage(Long id, String originFileImgName, String savedFileImgName, String uploadDir, String extension,
                       Long size, String contentType, String repImgYn) {
        super(id,originFileImgName,savedFileImgName,uploadDir,extension,size,contentType,repImgYn);

    }
}
