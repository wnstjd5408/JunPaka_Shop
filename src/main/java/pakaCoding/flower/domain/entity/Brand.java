package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
public class Brand extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Column(name = "brand_name")
    private String name;

    @Column(length = 700)
    private String brandComment;

    @ColumnDefault("0")
    private int count;

    @OneToMany(mappedBy = "brand")
    private Set<Item> items = new HashSet<>();

    //브랜드 제품 증가
    public void addBrandCount(){
        this.count += 1;
    }

    //브랜드 제품 감소
    public void subBrandCount(){
        this.count -= 1;
    }
}
