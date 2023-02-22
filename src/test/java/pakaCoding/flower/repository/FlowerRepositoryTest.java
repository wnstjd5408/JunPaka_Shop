package pakaCoding.flower.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.FlowerDto;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class FlowerRepositoryTest {
    @Autowired
    FlowerRepository flowerRepository;

    @Autowired
    TypeRepository typeRepository;

    @BeforeEach
    void beforeEach() {

    }

    @AfterEach
    void afterEach(){
        flowerRepository.deleteAll();
    }

    @Test
    public void typeMatch(){
        int id = typeRepository.findById(1).get().getId();

        log.info("id  = {}", id);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Flower> flowers = flowerRepository.findAllByTypeIdQuery(id, pageable);
        log.info("flowers.getTotalElements = {}", flowers.getTotalElements());

    }



    @Test
    public void save() {
        Type type1 = typeRepository.findById(1).get();

        log.info("id = {}",  type1.getId());

        Flower fs = registerFLower("장미꽃다발", 15000, 1, type1, 0L);
        Flower fBasket = registerFLower("장미꽃바구니", 35000, 1, type1, 0L);
        flowerRepository.save(fs);
        flowerRepository.save(fBasket);
        //when
        Flower findFlower = flowerRepository.findById(fs.getId()).orElseThrow(() ->
                new IllegalArgumentException("Wrong MemberId:<" + fs.getId() + ">"));


        assertThat(fs.getName()).isEqualTo(findFlower.getName());
        assertThat(fs.getPrice()).isEqualTo(findFlower.getPrice());

        assertThat(flowerRepository.findAll().size()).isEqualTo(2);
    }

    private Flower registerFLower(String name, int price, int stockQuantity, Type type, Long hitCount){
        return Flower.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .type(type)
                .delYn("N")
                .hitCount(hitCount)
                .build();
    }
}