package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.FlowerDto;
import pakaCoding.flower.repository.FlowerRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlowerService {
    private final FlowerRepository flowerRepository;
    private final FileService fileService;

    @Transactional
    public Long saveFlower(FlowerDto flowerDto) throws IOException {
        List<Flower> flowerList = flowerRepository.findAll();
        Flower flower = null;


        //insert
        if(flowerDto.getId() == null){
            flower = flowerDto.toEntity();
            flowerRepository.save(flower);
        }

        //파일저장
        fileService.saveFile(flowerDto);


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

    public Page<FlowerDto> findAllFlowers(Pageable pageable){
        log.info("findAllFlowers 시작");
        log.info("findAllFlowers 사용한 service repository 개수 ={}",
                flowerRepository.findAllByOrderByCreateDateDesc(pageable).stream().count());
        Page<Flower> flowerList = flowerRepository.findAllByOrderByCreateDateDesc(pageable);
        List<FlowerDto> flowerDtoList = flowerList.stream()
                .map(m -> FlowerDto.builder()
                        .id(m.getId())
                        .name(m.getName())
                        .price(m.getPrice())
                        .stockQuantity(m.getStockQuantity())
                        .hitCount(m.getHitCount())
                        .build())
                .collect(Collectors.toList());

        log.info("flowerDtoList.size()= {}", flowerDtoList.size());
        return new PageImpl<>(flowerDtoList, pageable, flowerDtoList.size());
    }


    public List<Flower> findFlowersType(int typeId){
        log.info("Flower Service findFlowersType 시작");
        log.info("findFlowersType 함수를 사용한 service repository 개수 ={}",
                flowerRepository.findAllByTypeIdQuery(typeId).stream().count());
        return flowerRepository.findAllByTypeIdQuery(typeId);
    }





}
