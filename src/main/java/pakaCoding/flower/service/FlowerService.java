package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.repository.FlowerRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlowerService {
    private final FlowerRepository flowerRepository;

    @Transactional
    public Long saveFlower(Flower flower){
        flowerRepository.save(flower);
        return flower.getId();
    }

    public Optional<Flower> findOne(Long flowerId){
        return flowerRepository.findById(flowerId);
    }

    public List<Flower> findFlowers(){
        log.info("service repository 개수 ={}", flowerRepository.findAll().stream().count());
        return flowerRepository.findAll(Sort.by(DESC, "createDate"));
    }

    public List<Flower> findFlowersType(Long typeId){
        log.info("service repository 개수 ={}", flowerRepository.findByTypeContainingOrderByNameDesc(typeId).stream().count());
        return flowerRepository.findByTypeContainingOrderByNameDesc(typeId);
    }



}
