package pakaCoding.flower.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity extends  TimeEntity{

    @CreatedBy
    @Column(updatable = false, length = 100)
    private String createdBy;


    @LastModifiedBy
    @Column(length = 100)
    private String modifiedBy;


}
