package pakaCoding.flower.service;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.repository.FlowerRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FlowerServiceTest {

    @InjectMocks
    private FlowerService flowerService;

    @Mock
    FlowerRepository repository;

    @BeforeEach
    void beforeEach(){

    }

    @Test
    void findItems() {

        //given
        Type flowerBasket = new Type(1, "꽃바구니", 0);
        Flower fs = registerFLower("장미꽃다발", 15000, 1, flowerBasket, 0L);
        Flower fBasket = registerFLower("장미꽃바구니", 35000, 1, flowerBasket, 0L);

        repository.save(fs);
        repository.save(fBasket);

        //when
        List<Flower> flowerList = repository.findAll();
        Flower flower = flowerList.get(0);

        assertThat(fs.getName()).isEqualTo(repository.findById(fs.getId()).get().getName());
        assertThat(flower.getName()).isEqualTo(fs.getName());


    }


    private Flower registerFLower(String name, int price, int stockQuantity, Type type, Long hitCount){
        return Flower.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .type(type)
                .hitCount(hitCount)
                .build();
    }
}