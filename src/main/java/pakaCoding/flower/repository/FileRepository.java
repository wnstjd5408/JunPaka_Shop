package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pakaCoding.flower.domain.entity.FileImage;

public interface FileRepository  extends JpaRepository<FileImage, Long> {
}
