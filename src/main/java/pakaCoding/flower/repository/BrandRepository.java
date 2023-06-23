package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pakaCoding.flower.domain.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
