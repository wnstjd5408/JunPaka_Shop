package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.Brand;
import pakaCoding.flower.domain.entity.BrandImage;
import pakaCoding.flower.domain.entity.Image;
import pakaCoding.flower.dto.BrandFormDto;
import pakaCoding.flower.dto.ImageDto;
import pakaCoding.flower.repository.BrandRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BrandService {



    private final BrandRepository brandRepository;
    private final ImageService imageService;

    public List<Brand> findAll(){
        return brandRepository.findAll();
    }


    public Long saveBrand(BrandFormDto brandFormDto){
        log.info("BrandService에서 saveBrand를 실행");


        Brand brand = brandFormDto.toEntity();
        brandRepository.save(brand);


        List<ImageDto> files = imageService.saveImageFile(brandFormDto.getBrandImages());
        Set<BrandImage> fileImages = files.stream().map(ImageDto::toEntity).collect(toSet());
        brand.addFiles(fileImages);


        return brand.getId();
    }
}
