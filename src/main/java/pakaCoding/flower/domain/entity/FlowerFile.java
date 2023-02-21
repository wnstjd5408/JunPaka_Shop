package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class FlowerFile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="flower_file_id")
    private Long id; //번호


    private Long flowerId;
    private String delYn;


    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;


    @Builder
    public FlowerFile(Long flowerId, File file) {
        this.flowerId = flowerId;
        this.delYn = "N";
        this.file = file;
    }
}
