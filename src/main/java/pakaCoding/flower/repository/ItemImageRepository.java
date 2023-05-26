package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pakaCoding.flower.domain.entity.ItemImage;

import java.util.List;
import java.util.Optional;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    List<ItemImage> findByFlowerId(Long flowerId);
    ItemImage findByFlowerIdAndRepImgYn(Long flowerId, String repImgYn);

    Optional<ItemImage> findBySavedFileImgName(String savedFileImgName);
}
