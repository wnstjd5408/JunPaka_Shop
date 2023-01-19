package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.service.repository.FlowerRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
        return flowerRepository.findOne(flowerId);
    }

    public List<Flower> findFlowers(){
        log.info("service respoitory 개수 ={}", flowerRepository.findAll().stream().count());
        return flowerRepository.findAll();
    }



}
