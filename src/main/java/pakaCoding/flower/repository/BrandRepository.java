package pakaCoding.flower.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pakaCoding.flower.domain.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

}
