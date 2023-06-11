package pakaCoding.flower.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.constant.FlowerSellStatus;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.AdminItemListDto;
import pakaCoding.flower.repository.FlowerRepository;
import pakaCoding.flower.repository.TypeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class FlowerServiceTest {


    @Autowired
    private FlowerService flowerService;

    @Autowired
    FlowerRepository flowerRepository;

    @Autowired
    TypeRepository typeRepository;

    @BeforeEach
    void beforeEach(){

    }

    @Test
    @DisplayName("관리자 전체 리스트 조회")
    void adminFindItems(){
        Type flowerBasket = new Type(1, "꽃바구니", 0);
        typeRepository.save(flowerBasket);


        Flower fs = registerFLower("테스트" , 1000, 11, flowerBasket);
        Flower fBasket = registerFLower("테스트" , 1000, 11, flowerBasket);

        flowerRepository.save(fs);
        flowerRepository.save(fBasket);


        Page<AdminItemListDto> adminItemListDtos = flowerService.adminPageFindAllFlowers(0);

        assertThat(adminItemListDtos.getTotalElements()).isEqualTo(2);

        for (AdminItemListDto adminItemListDto : adminItemListDtos) {

            System.out.println("adminItemListDto = " + adminItemListDto);
        }
    }


    @Test
    void findItems() {

        //given
        Type flowerBasket = new Type(1, "꽃바구니", 0);
        typeRepository.save(flowerBasket);

        Flower fs = registerFLower("테스트" , 1000, 11, flowerBasket);
        Flower fBasket = registerFLower("테스트" , 1000, 11, flowerBasket);

        flowerRepository.save(fs);
        flowerRepository.save(fBasket);

        //when
        List<Flower> flowerList = flowerRepository.findAll();
        Flower flower = flowerList.get(0);

        assertThat(fs.getName()).isEqualTo(flowerRepository.findById(fs.getId()).get().getName());
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