package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Flower extends TimeEntity {

    @Id
    @GeneratedValue
    @Column(name ="flower_id")
    private Long id;

    @Column(name = "flower_name")
    private String name;

    private int price;
    private int stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="type_id")
    private Type type;

    @ColumnDefault("0")
    private Integer hitCount;

    @Builder
    public Flower( String name, int price, int stockQuantity, Type type, Integer hitCount) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.type = type;
        this.hitCount = hitCount;
    }

}


