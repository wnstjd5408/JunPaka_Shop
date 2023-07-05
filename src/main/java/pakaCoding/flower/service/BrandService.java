package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.*;
import pakaCoding.flower.dto.BrandFormDto;
import pakaCoding.flower.dto.BrandItemListDto;
import pakaCoding.flower.dto.ImageDto;
import pakaCoding.flower.dto.MainItemDto;
import pakaCoding.flower.repository.BrandImageRepository;
import pakaCoding.flower.repository.BrandRepository;
import pakaCoding.flower.repository.ItemImageRepository;
import pakaCoding.flower.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BrandService {



    private final BrandRepository brandRepository;
    private final ItemRepository itemRepository;
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
    @Transactional(readOnly = true)
    public Page<BrandItemListDto> getBrandItem(long brandId, int page) {
        Pageable pageable = PageRequest.of(page, 15);
        Page<Item> brandItems = itemRepository.findBrandItems(brandId, pageable);


        List<BrandItemListDto> brandItemListDtos = brandItems.stream()
                .map(item -> {

                    log.info("item.getBrand().getName() = {}", item.getBrand().getName());
                    BrandItemListDto brandItemListDto = new BrandItemListDto(item);
                    brandItemListDto.addItemImgUrl(item.getItemImages().get(0).getSavedFileImgName());
                    return brandItemListDto;
                }).toList();


        return new PageImpl<>(brandItemListDtos, pageable, brandItems.getTotalElements());
    }
}
