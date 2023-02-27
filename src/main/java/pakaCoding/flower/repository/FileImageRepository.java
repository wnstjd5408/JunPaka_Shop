package pakaCoding.flower.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pakaCoding.flower.domain.entity.FileImage;
import pakaCoding.flower.dto.MainFlowerDto;

import java.io.File;
import java.util.List;

public interface FileImageRepository extends JpaRepository<FileImage, Long> {

    List<FileImage> findByFlowerIdOrderByIdDesc(Long flowerId);
    FileImage findByFlowerIdAndRepimgYn(Long flowerId, String repimgYn);

}
