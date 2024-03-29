package pakaCoding.flower.repository;

import lombok.extern.slf4j.Slf4j;
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
import pakaCoding.flower.domain.constant.ItemSellStatus;
import pakaCoding.flower.domain.entity.Brand;
import pakaCoding.flower.domain.entity.Item;
import pakaCoding.flower.domain.entity.ItemImage;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.MainItemDto;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@Slf4j
class ItmeRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    ItemImageRepository imageRepository;

    @Autowired
    BrandRepository brandRepository;

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

        Brand brand1 = Brand.builder()
                .brandComment("테스트")
                .name("1011 갤러리")
                .count('1')
                .build();

        Brand findBrand = brandRepository.save(brand1);

        for(int i = 1; i<=100; i++){
            Item is = registerItem("장미꽃다발", 15000, 1, type1,brand1);
            itemRepository.save(is);
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





        log.info("id  = {}", type1.getId());
        Pageable pageable = PageRequest.of(0, 10);
        Page<MainItemDto> items = itemRepository.findAllByTypeIdListDtos(type1.getId(), pageable);
        log.info("items.getTotalElements = {}", items.getTotalElements());
        log.info("item 사이즈 ={}", items.getSize());

    }



    @Test
    public void save() {
        Type type1 = Type.builder()
                .count(0)
                .id(1)
                .typename("꽃바구니")
                .build();
        typeRepository.save(type1);

        Brand brand1 = Brand.builder()
                .brandComment("테스트")
                .name("1011 갤러리")
                .count('1')
                .build();

        Brand findBrand = brandRepository.save(brand1);
        log.info("id = {}",  type1.getId());

        Item is = registerItem("장미꽃다발", 15000, 1, type1, brand1);
        Item iBasket = registerItem("장미꽃바구니", 35000, 1, type1, brand1);
        itemRepository.save(is);
        itemRepository.save(iBasket);
        //when
        Item findItem = itemRepository.findById(is.getId()).orElseThrow(() ->
                new IllegalArgumentException("Wrong MemberId:<" + is.getId() + ">"));


        assertThat(is.getName()).isEqualTo(findItem.getName());
        assertThat(is.getPrice()).isEqualTo(findItem.getPrice());

        assertThat(itemRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Brand Test")
    public void findBrand(){


        Type type1 = Type.builder()
                .count(0)
                .id(1)
                .typename("꽃바구니")
                .build();

        typeRepository.save(type1);

        Brand brand1 = Brand.builder()
                .brandComment("테스트")
                .name("1011 갤러리")
                .count('1')
                .build();

        Brand findBrand = brandRepository.save(brand1);


        for(int i = 1; i<=100; i++){
            Item is = registerItem("장미꽃다발" + i, 15000, 1, type1, brand1);
            itemRepository.save(is);
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

        Pageable pageable = PageRequest.of(0, 10);


        Page<Item> brandItems = itemRepository.findBrandItems(findBrand.getId(), pageable);
        log.info("brandItems.getTotalElements = {}", brandItems.getTotalElements());
        log.info("brandItems.size = {}", brandItems.getSize());


        brandItems.forEach(item -> {

            log.info("brand Name = {}", brand1.getName());
        });


    }

    private Item registerItem(String name, int price, int stockQuantity, Type type, Brand brand){
        return Item.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .brand(brand)
                .type(type)
                .itemSellStatus(ItemSellStatus.SELL)
                .delYn("N")
                .hitCount(0L)
                .build();
    }
}