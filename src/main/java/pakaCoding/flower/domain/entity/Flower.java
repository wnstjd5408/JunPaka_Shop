package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pakaCoding.flower.dto.FlowerDto;

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

    private int price;          //가격
    private int stockQuantity;  //수량

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="type_id")
    private Type type;

    @ColumnDefault("0L")
    private Long hitCount;      //조회수


    private String delYn;       //삭제여부


    public Flower delete(String delYn){
        this.delYn = delYn;
        return this;
    }



    @Builder
    public Flower(FlowerDto flowerDto, Type type) {
        this.name = flowerDto.getName();
        this.price = flowerDto.getPrice();
        this.stockQuantity = flowerDto.getStockQuantity();
        this.type = type;
        this.hitCount = 0L;
        this.delYn = "N";
    }
}


