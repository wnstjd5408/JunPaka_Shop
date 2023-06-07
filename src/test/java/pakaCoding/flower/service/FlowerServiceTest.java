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
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.constant.FlowerSellStatus;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.repository.FlowerRepository;
import pakaCoding.flower.service.FlowerService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
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
        Flower fs = registerFLower("테스트" , 1000, 11, flowerBasket);
        Flower fBasket = registerFLower("테스트" , 1000, 11, flowerBasket);

        repository.save(fs);
        repository.save(fBasket);

        //when
        List<Flower> flowerList = repository.findAll();
        Flower flower = flowerList.get(0);

        assertThat(fs.getName()).isEqualTo(repository.findById(fs.getId()).get().getName());
        assertThat(flower.getName()).isEqualTo(fs.getName());


    }


    private Flower registerFLower(String name, int price, int stockQuantity, Type type){
        return Flower.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .type(type)
                .flowerSellStatus(FlowerSellStatus.SELL)
                .hitCount(0L)
                .fileImages(new ArrayList<>())
                .delYn("Y")
                .build();
    }

}