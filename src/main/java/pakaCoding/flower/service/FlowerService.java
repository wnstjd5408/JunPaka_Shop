package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.File;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.dto.FlowerDto;
import pakaCoding.flower.repository.FlowerRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlowerService {
    private final FlowerRepository flowerRepository;
    private final FileService fileService;

    @Transactional
    public Long saveFlower(FlowerDto flowerDto) throws Exception {
        log.info("FlowerService에서 saveFlower 실행");
        Flower flower = null;
        log.info("flowerDto.getId() = {}" , flowerDto.getId());



        //insert
        if(flowerDto.getId() == null){
            flower = flowerDto.toEntity();

            //파일저장
            List<File> files = fileService.saveFile(flowerDto);
            flower.addFiles(files);
            log.info("flower.getFiles ={}", flower.getFiles().get(0).getOriginFileName());
            flowerRepository.save(flower);
        }
        //update
        else{
            flower = flowerRepository.findById(flowerDto.getId()).get();
        }




        log.info("flower.getId() = {}", flower.getId());
        return flower.getId();
    }

    public Optional<Flower> findOne(Long flowerId){
        return flowerRepository.findById(flowerId);
    }

    public List<Flower> findFlowers(){
        log.info("Flower Service findFlowers 시작");
        log.info("findFlowers를 사용한 service repository 개수 ={}",
                flowerRepository.findAll().stream().count());
        return flowerRepository.findAll(Sort.by(DESC, "createDate"));
    }


    public List<Flower> findFlowersType(int typeId){
        log.info("Flower Service findFlowersType 시작");
        log.info("findFlowersType 함수를 사용한 service repository 개수 ={}",
                flowerRepository.findAllByTypeIdQuery(typeId).stream().count());
        return flowerRepository.findAllByTypeIdQuery(typeId);
    }





}
