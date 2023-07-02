package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Brand extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Column(name = "brand_name")
    @NotNull
    private String name;

    @Column(length = 700)
    @NotNull
    private String brandComment;

    @ColumnDefault("0")
    private int count;

    @OneToMany(mappedBy = "brand")
    private Set<Item> items = new HashSet<>();

    @OneToMany(mappedBy = "brand", cascade = CascadeType.PERSIST)
    private Set<BrandImage> brandImages = new HashSet<>();

    @Builder
    public Brand(String name, String brandComment, int count) {
        this.name = name;
        this.brandComment = brandComment;
        this.count = count;
    }

    public void addFiles(Set<BrandImage> brandImages){
        this.brandImages = brandImages;
        brandImages.forEach(bi -> bi.setBrand(this));
    }



    //브랜드 제품 증가
    public void addBrandCount(){
        this.count += 1;
    }

    //브랜드 제품 감소
    public void subBrandCount(){
        this.count -= 1;
    }
}
