package pakaCoding.flower.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pakaCoding.flower.domain.constant.ItemSellStatus;
import pakaCoding.flower.domain.entity.Item;
import pakaCoding.flower.domain.entity.Image;
import pakaCoding.flower.domain.entity.ItemImage;
import pakaCoding.flower.domain.entity.Type;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class FileImageRepositoryTest {

    @Autowired
    ItemImageRepository imageRepository;
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    TypeRepository typeRepository;


    @BeforeEach
    void beforeEach() {

    }

    @AfterEach
    void afterEach(){
        imageRepository.deleteAll();
    }


    @Test
    public void fileImageTest(){
        Item flower1 = itemRepository.findById(1L).get();

        List<ItemImage> byFlowerId = imageRepository.findByItemId(flower1.getId());

        log.info("1번의 개수 = {}", byFlowerId.size());
    }

    @Test
    public void fileImageTest2(){
        Type type1 = typeRepository.findById(1).get();

        for (int i = 1; i <= 100; i++) {
            Item fs = registerItem("장미꽃다발", 15000, 1, type1);
            itemRepository.save(fs);
            ItemImage image = ItemImage.builder()
                    .repImgYn("Y")
                    .contentType("10000")
                    .extension("1")
                    .originFileImgName("1")
                    .savedFileImgName("!")
                    .size(1000L)
                    .uploadDir("www")
                    .build();
            imageRepository.save(image);
        }
        List<Image> fileImageList = new ArrayList<>();

        for(Long i = 1L; i<=100L; i+= 1L){

            Item flower = itemRepository.findById(i).get();
            Image image = imageRepository.findByItemIdAndRepImgYn(flower.getId(), "Y");
            fileImageList.add(image);
        }

        log.info("file = {}", fileImageList.size());

    }

    private Item registerItem(String name, int price, int stockQuantity, Type type){
        return Item.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .type(type)
                .itemSellStatus(ItemSellStatus.SELL)
                .delYn("N")
                .hitCount(0L)
                .build();
    }
}