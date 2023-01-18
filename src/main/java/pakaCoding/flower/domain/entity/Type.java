package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Type {

    @Id @GeneratedValue
    @Column(name = "tpye_id")
    private int id;

    @ColumnDefault("0")
    private Integer count;

    @OneToMany(mappedBy = "type")
    private List<Flower> flowers = new ArrayList<>();


}
