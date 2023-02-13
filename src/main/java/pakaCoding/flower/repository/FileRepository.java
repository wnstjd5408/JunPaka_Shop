package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.UploadFile;

@Repository
@Transactional
public interface FileRepository extends JpaRepository<UploadFile, Long> {
}
