package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "fileImage")
public class FileImage extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="file_image_id")
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



    private String repimgYn; //대표이미지 여부

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flower_id")
    private Flower flower;


    @Builder
    public FileImage(Long id, String originFileImgName, String savedFileImgName, String uploadDir, String extension, Long size, String contentType, Flower flower, String repimgYn) {

        this.id = id;
        this.originFileImgName = originFileImgName;
        this.savedFileImgName = savedFileImgName;
        this.uploadDir = uploadDir;
        this.extension = extension;
        this.size = size;
        this.contentType = contentType;
        this.repimgYn = repimgYn;
        this.flower = flower;
    }


    public void updateFlowerImg(String originFileImgName, String savedFileImgName, String uploadDir, String extension, Long size, String contentType, String repimgYn){
        this.originFileImgName = originFileImgName;
        this.savedFileImgName = savedFileImgName;
        this.uploadDir = uploadDir;
        this.extension = extension;
        this.size = size;
        this.contentType = contentType;
        this.repimgYn = repimgYn;
    }
}
