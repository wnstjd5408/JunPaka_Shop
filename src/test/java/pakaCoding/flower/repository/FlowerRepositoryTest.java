package pakaCoding.flower.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import pakaCoding.flower.domain.constant.FlowerSellStatus;
import pakaCoding.flower.domain.entity.FileImage;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.MainFlowerDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.yml")
@Slf4j
class FlowerRepositoryTest {
    @Autowired
    FlowerRepository flowerRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    FileImageRepository fileImageRepository;

    @BeforeEach
    void beforeEach() {

    }
//
//    @AfterEach
//    void afterEach(){
//        flowerRepository.deleteAll();
//    }

    @Test
    public void typeMatch(){
        Type type1 = Type.builder()
                .count(0)
                .id(1)
                .typename("꽃바구니")
                .build();
        typeRepository.save(type1);

        for(int i = 1; i<=100; i++){
            Flower fs = registerFLower("장미꽃다발", 15000, 1, type1);
            flowerRepository.save(fs);
            FileImage fileImage = FileImage.builder()
                    .repimgYn("Y")
                    .contentType("10000")
                    .extension("1")
                    .originFileImgName("1")
                    .savedFileImgName("!")
                    .size(1000L)
                    .uploadDir("www")
                    .flower(fs)
                    .build();
            fileImageRepository.save(fileImage);
        }





        log.info("id  = {}", type1.getId());
        Pageable pageable = PageRequest.of(0, 10);
        Page<MainFlowerDto> flowers = flowerRepository.findAllByTypeIdListDtos(type1.getId(), pageable);
        log.info("flowers.getTotalElements = {}", flowers.getTotalElements());
        log.info("flower 사이즈 ={}", flowers.getSize());
        List<FileImage> byFlowerIdOrderByIdDesc = fileImageRepository.findByFlowerId(flowers.getContent().get(0).getId());
        log.info("FileImage 0번의 개수 = {}", byFlowerIdOrderByIdDesc.size());
    }



    @Test
    public void save() {
        Type type1 = Type.builder()
                .count(0)
                .id(1)
                .typename("꽃바구니")
                .build();
        typeRepository.save(type1);

        log.info("id = {}",  type1.getId());

        Flower fs = registerFLower("장미꽃다발", 15000, 1, type1);
        Flower fBasket = registerFLower("장미꽃바구니", 35000, 1, type1);
        flowerRepository.save(fs);
        flowerRepository.save(fBasket);
        //when
        Flower findFlower = flowerRepository.findById(fs.getId()).orElseThrow(() ->
                new IllegalArgumentException("Wrong MemberId:<" + fs.getId() + ">"));


        assertThat(fs.getName()).isEqualTo(findFlower.getName());
        assertThat(fs.getPrice()).isEqualTo(findFlower.getPrice());

        assertThat(flowerRepository.findAll().size()).isEqualTo(2);
    }

    private Flower registerFLower(String name, int price, int stockQuantity, Type type){
        return Flower.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .type(type)
                .flowerSellStatus(FlowerSellStatus.SELL)
                .delYn("N")
                .hitCount(0L)
                .build();
    }
}