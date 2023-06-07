package pakaCoding.flower.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.ItemImage;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.*;
import pakaCoding.flower.repository.FlowerRepository;
import pakaCoding.flower.repository.ItemImageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlowerService {
    private final FlowerRepository flowerRepository;
    private final FileImageService fileImageService;
    private final ItemImageRepository fileImageRepository;

    @Transactional
    public Long saveFlower(FlowerFormDto flowerFormDto) throws Exception {
        Flower flower = null;
        log.info("FlowerService에서 saveFlower 실행");

        //insert
        if(flowerFormDto.getId() == null){
            flower = flowerFormDto.toEntity();
            flowerRepository.save(flower);
        }
        //상품 update
        else{
            flower = flowerRepository.findById(flowerFormDto.getId()).get();
        }

        //파일저장
        List<ImageDto> files = fileImageService.saveFile(flowerFormDto);
        List<ItemImage> fileImages = files.stream().map(ImageDto::toEntity).collect(Collectors.toList());
        flower.addFiles(fileImages);

        log.info("flower.getId() = {}", flower.getId());

        Type type = flower.getType();
        type.addTypeCount();

        return flower.getId();
    }
    @Transactional(readOnly = true)
    public FlowerDetailDto findOne(Long flowerId){

        // 상품 이미지 엔티티들을 fileImageDto 객체로 변환하여 fileImageDtoList에 담습니다.
        List<ItemImage> fileImageList = fileImageRepository.findByFlowerId(flowerId);
        List<String> imgUrlList = new ArrayList<>();

        for (ItemImage fileImage : fileImageList) {
            ImageDto fileImageDto = new ImageDto(fileImage);
            log.info("fileImageDto.getImgUrl() = {} ", fileImageDto.getImgUrl());
            imgUrlList.add(fileImageDto.getImgUrl());
        }
        Flower flower = flowerRepository.findById(flowerId).orElseThrow(EntityNotFoundException::new);
        return new FlowerDetailDto(flower, imgUrlList);
    }

    @Transactional(readOnly = true)
    public Page<AdminItemListDto> adminPageFindAllFlowers(int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Flower> adminFlowers = flowerRepository.findAdminFlowers(pageable);

        return adminFlowers.map(f -> AdminItemListDto.builder()
                .itemName(f.getName())
                .stockQuantity(f.getStockQuantity())
                .price(f.getPrice())
                .id(f.getId())
                .build());
    }

    @Transactional(readOnly = true)
    //list에 기본 페이지 Select
    public Page<MainFlowerDto> findAllFlowers(int page){
        Pageable pageable = PageRequest.of(page, 8);
        log.info("findAllFlowers 시작");

        Page<MainFlowerDto> flowerList = flowerRepository.findFlowerListDto(pageable);
        List<MainFlowerDto> flowerDtoList = flowerList.getContent();

        return new PageImpl<>(flowerDtoList, pageable, flowerList.getTotalElements());
    }

    @Transactional(readOnly = true)
    //type별로 출력
    public Page<MainFlowerDto> findFlowersType(int typeId, int page){
        Pageable pageable = PageRequest.of(page, 16);
        log.info("Flower Service findFlowersType 시작");
        log.info("findFlowersType 함수를 사용한 service repository 개수 ={}",
                flowerRepository.findAllByTypeIdListDtos(typeId, pageable).stream().count());
        Page<MainFlowerDto> flowerList = flowerRepository.findAllByTypeIdListDtos(typeId, pageable);
        List<MainFlowerDto> flowerDtoList = flowerList.getContent();
        return new PageImpl<>(flowerDtoList, pageable, flowerList.getTotalElements());
    }


    //FlowerList를 Dto로 변경
    private PageImpl<MainFlowerDto> getMainFlowerDtos(Page<Flower> flowerList, Pageable pageable){
        List<MainFlowerDto> flowerDtoList = flowerList.stream()
                .map(m -> MainFlowerDto.builder()
                        .id(m.getId())
                        .flowerName(m.getName())
                        .price(m.getPrice())
                        .imgURL(m.getFileImages().get(0).getSavedFileImgName())
                        .build())
                .collect(Collectors.toList());
        return new PageImpl<>(flowerDtoList, pageable, flowerList.getTotalElements());
    }




}
