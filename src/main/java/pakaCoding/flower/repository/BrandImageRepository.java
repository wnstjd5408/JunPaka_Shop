package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pakaCoding.flower.domain.entity.BrandImage;

import java.util.List;

public interface BrandImageRepository extends JpaRepository<BrandImage, Long> {


    List<BrandImage> findByBrandId(Long brandId);

}
