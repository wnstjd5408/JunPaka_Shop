package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.FileImage;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.dto.FlowerFormDto;
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
        log.info("FlowerService에서 saveFlower 실행");
        Flower flower = null;
        log.info("flowerFormDto.getId() = {}" , flowerFormDto.getId());

        //insert
        if(flowerFormDto.getId() == null){
            flower = flowerFormDto.toEntity();
            flowerRepository.save(flower);
        }
        //update
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

    //Pageing 전
//    public List<Flower> findFlowers(){
//        log.info("Flower Service findFlowers 시작");
//        log.info("findFlowers를 사용한 service repository 개수 ={}",
//                flowerRepository.findAll().stream().count());
//        return flowerRepository.findAll(Sort.by(DESC, "createDate"));
//    }

    public Page<FlowerFormDto> findAllFlowers(int page){
        Pageable pageable = PageRequest.of(page, 16);
        log.info("findAllFlowers 시작");
        log.info("findAllFlowers 사용한 service repository 개수 ={}",
                flowerRepository.findAllByOrderByCreateDateDesc(pageable).stream().count());
        Page<Flower> flowerList = flowerRepository.findAll(pageable);
        List<FlowerFormDto> flowerDtoList = flowerList.stream()
                .map(m -> FlowerFormDto.builder()
                        .id(m.getId())
                        .name(m.getName())
                        .price(m.getPrice())
                        .stockQuantity(m.getStockQuantity())
                        .hitCount(m.getHitCount())
                        .build())
                .collect(Collectors.toList());

        log.info("flowerList.getTotalElements()= {}", flowerList.getTotalElements());
        return new PageImpl<>(flowerDtoList, pageable, flowerList.getTotalElements());
    }


    public Page<FlowerFormDto> findFlowersType(int typeId, int page){
        Pageable pageable = PageRequest.of(page, 16);
        log.info("Flower Service findFlowersType 시작");
        log.info("findFlowersType 함수를 사용한 service repository 개수 ={}",
                flowerRepository.findAllByTypeIdQuery(typeId, pageable).stream().count());
        Page<Flower> flowerList = flowerRepository.findAllByTypeIdQuery(typeId, pageable);
        List<FlowerFormDto> flowerDtoList = flowerList.stream()
                .map(m -> FlowerFormDto.builder()
                        .id(m.getId())
                        .name(m.getName())
                        .price(m.getPrice())
                        .stockQuantity(m.getStockQuantity())
                        .hitCount(m.getHitCount())
                        .build())
                .collect(Collectors.toList());
        return new PageImpl<>(flowerDtoList, pageable, flowerList.getTotalElements());
    }





}
