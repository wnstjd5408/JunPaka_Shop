package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pakaCoding.flower.domain.entity.FileImage;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.dto.FlowerFormDto;
import pakaCoding.flower.dto.MainFlowerDto;
import pakaCoding.flower.repository.FileImageRepository;
import pakaCoding.flower.repository.FlowerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlowerService {
    private final FlowerRepository flowerRepository;
    private final FileImageService fileImageService;

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
        List<FileImage> files = fileImageService.saveFile(flowerFormDto);
        flower.addFiles(files);

        log.info("flower.getId() = {}", flower.getId());
        return flower.getId();
    }

    public Optional<Flower> findOne(Long flowerId){
        return flowerRepository.findById(flowerId);
    }

    //list에 기본 페이지 Select
    public Page<MainFlowerDto> findAllFlowers(int page){
        Pageable pageable = PageRequest.of(page, 16);
        log.info("findAllFlowers 시작");
        log.info("findAllFlowers 사용한 service repository 개수 ={}",
                flowerRepository.findAll(pageable).stream().count());
        Page<Flower> flowerList = flowerRepository.findAll(pageable);
        return getMainFlowerDtos(flowerList, pageable);
    }

    //type별로 출력
    public Page<MainFlowerDto> findFlowersType(int typeId, int page){
        Pageable pageable = PageRequest.of(page, 16);
        log.info("Flower Service findFlowersType 시작");
        log.info("findFlowersType 함수를 사용한 service repository 개수 ={}",
                flowerRepository.findAllByTypeIdQuery(typeId, pageable).stream().count());
        Page<Flower> flowerList = flowerRepository.findAllByTypeIdQuery(typeId, pageable);
        return getMainFlowerDtos(flowerList, pageable);
    }



    private PageImpl<MainFlowerDto> getMainFlowerDtos(Page<Flower> flowerList, Pageable pageable){
        List<MainFlowerDto> flowerDtoList = flowerList.stream()
                .map(m -> MainFlowerDto.builder()
                        .id(m.getId())
                        .flowerName(m.getName())
                        .price(m.getPrice())
                        .uploadDir(m.getFileImages().get(0).getUploadDir())
                        .imgURL(m.getFileImages().get(0).getSavedFileImgName())
                        .build())
                .collect(Collectors.toList());
        return new PageImpl<>(flowerDtoList, pageable, flowerList.getTotalElements());
    }




}
