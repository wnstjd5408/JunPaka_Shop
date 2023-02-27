package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pakaCoding.flower.domain.entity.FileImage;

public interface FileImageRepository extends JpaRepository<FileImage, Long> {
}
