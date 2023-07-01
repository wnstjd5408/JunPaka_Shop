package pakaCoding.flower.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;

@Table(name = "persistent_logins")
@Entity
@Getter
public class PersistentLogins {


    @Id
    @Column(length = 64)
    private String series;



    @Column(length = 64)
    private String username;


    @Column(length = 64)
    private String token;

    @Column(length = 64, name = "last_used")
    private LocalDateTime lastUsed;
}
