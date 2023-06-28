package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import pakaCoding.flower.domain.constant.ItemSellStatus;
import pakaCoding.flower.dto.ItemFormDto;
import pakaCoding.flower.exception.OutOfStockException;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="item_id")
    private Long id;            //번호

    @Column(name = "item_name", length = 100)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="brand_id")
    private Brand brand;

    @ColumnDefault("0")
    private Long hitCount;      //조회수

    @NotNull
    private String delYn;       //삭제여부

    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST)
    private List<ItemImage> itemImages = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;


    public void delete(){
        this.delYn = "Y";
    }



    public void addFiles(List<ItemImage> itemImages){
        this.itemImages = itemImages;
        itemImages.forEach(ii -> ii.setItem(this));
    }


    @Builder
    public Item(ItemFormDto itemFormDto) {
        this.name = itemFormDto.getName();
        this.price = itemFormDto.getPrice();
        this.stockQuantity = itemFormDto.getStockQuantity();
        this.detailComment = itemFormDto.getDetailComment();
        this.type = itemFormDto.getType();
        this.itemSellStatus =itemFormDto.getItemSellStatus();
        this.hitCount = 0L;
        this.delYn = "N";
    }

    public void updateItem(ItemFormDto itemFormDto){
        this.name = itemFormDto.getName();
        this.price = itemFormDto.getPrice();
        this.stockQuantity = itemFormDto.getStockQuantity();
        this.detailComment = itemFormDto.getDetailComment();
        this.type = itemFormDto.getType();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
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


