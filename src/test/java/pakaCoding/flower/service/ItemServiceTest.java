package pakaCoding.flower.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.constant.ItemSellStatus;
import pakaCoding.flower.domain.entity.Item;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.AdminItemListDto;
import pakaCoding.flower.repository.ItemRepository;
import pakaCoding.flower.repository.TypeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class ItemServiceTest {


    @Autowired
    private ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

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


        Item fs = registerItems("테스트" , 1000, 11, flowerBasket);
        Item fBasket = registerItems("테스트" , 1000, 11, flowerBasket);

        itemRepository.save(fs);
        itemRepository.save(fBasket);


        Page<AdminItemListDto> adminItemListDtos = itemService.adminPageFindAllItems(0);

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

        Item fs = registerItems("테스트" , 1000, 11, flowerBasket);
        Item fBasket = registerItems("테스트" , 1000, 11, flowerBasket);

        itemRepository.save(fs);
        itemRepository.save(fBasket);

        //when
        List<Item> flowerList = itemRepository.findAll();
        Item flower = flowerList.get(0);

        assertThat(fs.getName()).isEqualTo(itemRepository.findById(fs.getId()).get().getName());
        assertThat(flower.getName()).isEqualTo(fs.getName());


    }


    private Item registerItems(String name, int price, int stockQuantity, Type type){
        return Item.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .type(type)
                .itemSellStatus(ItemSellStatus.SELL)
                .hitCount(0L)
                .itemImages(new ArrayList<>())
                .delYn("Y")
                .build();
    }

}