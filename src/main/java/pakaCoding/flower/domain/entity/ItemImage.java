package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("I")
public class ItemImage extends  Image{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    @Builder
    public ItemImage(Long id, String originFileImgName, String savedFileImgName, String uploadDir, String extension,
                       Long size, String contentType, String repImgYn) {
        super(id,originFileImgName,savedFileImgName,uploadDir,extension,size,contentType,repImgYn);

    }
}
