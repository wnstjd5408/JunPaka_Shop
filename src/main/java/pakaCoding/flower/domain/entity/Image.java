package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class Image extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    //고객이 업로드한 파일명
    @Column(nullable = false)
    private String originFileImgName;

    //서버 내부에서 관리하는 파일명명
    @Column(nullable = false)
    private String savedFileImgName;

    private String uploadDir;   //경로명

    private String extension;   //확장자

    private Long size;          //파일사이즈

    private String contentType; //ContentType

    private String repImgYn; //대표이미지 여부




    public void updateFlowerImg(String originFileImgName, String savedFileImgName, String uploadDir, String extension, Long size, String contentType, String repImgYn){
        this.originFileImgName = originFileImgName;
        this.savedFileImgName = savedFileImgName;
        this.uploadDir = uploadDir;
        this.extension = extension;
        this.size = size;
        this.contentType = contentType;
        this.repImgYn = repImgYn;
    }
}
