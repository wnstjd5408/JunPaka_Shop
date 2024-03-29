package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private int id;

    @NotNull
    private String typename;

    @ColumnDefault("0")
    private Integer count;

    @OneToMany(mappedBy = "type")
    private List<Item> items = new ArrayList<>();

    @Builder
    public Type(int id, String typename, Integer count) {
        this.id = id;
        this.typename = typename;
        this.count = count;
    }

    public void addTypeCount() {
        this.count += 1;
    }

    public void subTypeCount(){
        this.count -= 1;
    }
}
