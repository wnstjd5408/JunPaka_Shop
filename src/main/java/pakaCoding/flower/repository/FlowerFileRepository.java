package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pakaCoding.flower.domain.entity.FlowerFile;

public interface FlowerFileRepository  extends JpaRepository<FlowerFile, Long> {
}
