package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pakaCoding.flower.domain.constant.FlowerSellStatus;
import pakaCoding.flower.dto.FlowerFormDto;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="type_id")
    private Type type;

    private Long hitCount;      //조회수


    private String delYn;       //삭제여부

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

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


//    public void addFiles(List<FileImage> files){
//        this.files = files;
//        files.forEach(file -> file.setFlower(this));
//    }


    @Builder
    public Flower(FlowerFormDto flowerDto) {
        this.name = flowerDto.getName();
        this.price = flowerDto.getPrice();
        this.stockQuantity = flowerDto.getStockQuantity();
        this.type = flowerDto.getType();
        this.flowerSellStatus =flowerDto.getFlowerSellStatus();
        this.hitCount = 0L;
        this.delYn = "N";
    }
}


