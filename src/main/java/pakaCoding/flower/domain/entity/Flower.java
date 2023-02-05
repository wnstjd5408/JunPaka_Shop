package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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



}


