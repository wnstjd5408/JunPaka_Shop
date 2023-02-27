package pakaCoding.flower.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pakaCoding.flower.domain.constant.FlowerSellStatus;
import pakaCoding.flower.domain.entity.FileImage;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.Type;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class FileImageRepositoryTest {

    @Autowired
    FileImageRepository fileImageRepository;
    @Autowired
    FlowerRepository flowerRepository;

    @Autowired
    TypeRepository typeRepository;


    @BeforeEach
    void beforeEach() {

    }

    @AfterEach
    void afterEach(){
        fileImageRepository.deleteAll();
    }


    @Test
    public void fileImageTest(){
        Flower flower1 = flowerRepository.findById(1L).get();

        List<FileImage> byFlowerIdOrderByIdDesc = fileImageRepository.findByFlowerIdOrderByIdDesc(flower1.getId());

        log.info("1번의 개수 = {}", byFlowerIdOrderByIdDesc.size());

    }

    @Test
    public void fileImageTest2(){
        Type type1 = typeRepository.findById(1).get();

        for (int i = 1; i <= 100; i++) {
            Flower fs = registerFLower("장미꽃다발", 15000, 1, type1);
            flowerRepository.save(fs);
            FileImage fileImage = FileImage.builder()
                    .repimgYn("Y")
                    .contentType("10000")
                    .extension("1")
                    .originFileName("1")
                    .savedFileName("!")
                    .size(1000L)
                    .uploadDir("www")
                    .flower(fs)
                    .build();
            fileImageRepository.save(fileImage);
        }
        List<FileImage> fileImageList = new ArrayList<>();

        for(Long i = 1L; i<=100L; i+= 1L){

            Flower flower = flowerRepository.findById(i).get();
            FileImage fileImage = fileImageRepository.findByFlowerIdAndRepimgYn(flower.getId(), "Y");
            fileImageList.add(fileImage);
        }

        log.info("file = {}", fileImageList.size());

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