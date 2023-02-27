package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pakaCoding.flower.domain.entity.FileImage;

public interface FileImgRepository extends JpaRepository<FileImage, Long> {
}
