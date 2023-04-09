package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pakaCoding.flower.domain.constant.FlowerSellStatus;
import pakaCoding.flower.dto.FlowerFormDto;
import pakaCoding.flower.exception.OutOfStockException;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Flower extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="flower_id")
    private Long id;            //번호

    @Column(name = "flower_name")
    private String name;        //이름

    @Column(nullable = false)
    private int price;          //가격

    @Column(nullable = false)
    private int stockQuantity;  //수량


    @Column(length = 1000)
    private String detailComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="type_id")
    private Type type;

    private Long hitCount;      //조회수


    private String delYn;       //삭제여부

    @OneToMany(mappedBy = "flower", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<FileImage> fileImages = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private FlowerSellStatus flowerSellStatus;


    public Flower delete(String delYn){
        this.delYn = delYn;
        return this;
    }

//    public Flower update(List<FileImage> files){
//        this.files = files;
//        return this;
//    }


    public void addFiles(List<FileImage> fileImages){
        this.fileImages = fileImages;
        fileImages.forEach(fileImage -> fileImage.setFlower(this));
    }


    @Builder
    public Flower(FlowerFormDto flowerDto) {
        this.name = flowerDto.getName();
        this.price = flowerDto.getPrice();
        this.stockQuantity = flowerDto.getStockQuantity();
        this.detailComment = flowerDto.getDetailComment();
        this.type = flowerDto.getType();
        this.flowerSellStatus =flowerDto.getFlowerSellStatus();
        this.hitCount = 0L;
        this.delYn = "N";
    }

    public void removeStockQuantity(int stock){

        int restStock = this.stockQuantity - stock;
        if(restStock <0){
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량 : " + this.stockQuantity + ")");

        }
        this.stockQuantity = restStock;
    }

    public void addStockQuantity(int stock){
        this.stockQuantity += stock;
    }
}


