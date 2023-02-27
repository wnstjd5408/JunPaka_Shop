package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "file")
public class FileImage extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="file_id")
    private Long id;


    //고객이 업로드한 파일명
    @Column(nullable = false)
    private String originFileName;

    //서버 내부에서 관리하는 파일명명
    @Column(nullable = false)
    private String savedFileName;


    private String uploadDir;   //경로명

    private String extension;   //확장자

    private Long size;          //파일사이즈

    private String contentType; //ContentType

    private String repimgYn;; //대표이미지 여부


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flower_id")
    private Flower flower;

    @Builder
    public FileImage(Long id, String originFileName, String savedFileName, String uploadDir, String extension, Long size, String contentType, Flower flower, String repimgYn) {
        this.id = id;
        this.originFileName = originFileName;
        this.savedFileName = savedFileName;
        this.uploadDir = uploadDir;
        this.extension = extension;
        this.size = size;
        this.flower = flower;
        this.contentType = contentType;
        this.repimgYn =repimgYn;
    }

    public void recipeThumbNail(MultipartFile multipartFile){
        this.repimgYn = "Y";
    }
}
