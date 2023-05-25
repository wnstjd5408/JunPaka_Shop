package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name="review_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewImage extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;




}
