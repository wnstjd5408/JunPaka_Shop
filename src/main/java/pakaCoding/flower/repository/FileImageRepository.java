package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pakaCoding.flower.domain.entity.FileImage;

import java.util.List;
import java.util.Optional;

public interface FileImageRepository extends JpaRepository<FileImage, Long> {

    List<FileImage> findByFlowerId(Long flowerId);
    FileImage findByFlowerIdAndRepimgYn(Long flowerId, String repimgYn);

    Optional<FileImage> findBySavedFileImgName(String savedFileImgName);
}
