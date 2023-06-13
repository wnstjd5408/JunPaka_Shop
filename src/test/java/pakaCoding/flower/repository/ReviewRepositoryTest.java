package pakaCoding.flower.repository;

import lombok.extern.slf4j.Slf4j;
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
import pakaCoding.flower.domain.constant.Gender;
import pakaCoding.flower.domain.constant.Role;
import pakaCoding.flower.domain.entity.*;

import java.time.LocalDate;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})

//@TestPropertySource(locations = "classpath:application-test.yml")

@Slf4j
class ReviewRepositoryTest {


    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("flowerId로 검색")
    public void findFlowerID(){
        Member saveMember = getMember();
        Item saveFlower1 = itemRepository.save(registerFLower("테스트꽃1", 10000, 10));
        Item saveFlower2 = itemRepository.save(registerFLower("테스트꽃2", 10000, 10));
        Item saveFlower3 = itemRepository.save(registerFLower("테스트꽃3", 10000, 10));

        Review review1 = Review.builder().item(saveFlower1).comment("테스트입니다.").rating(5).member(saveMember).build();
        Review review2 = Review.builder().item(saveFlower1).comment("테스트입니다.").rating(5).member(saveMember).build();
        Review review3 = Review.builder().item(saveFlower1).comment("테스트입니다.").rating(5).member(saveMember).build();
        Review review4 = Review.builder().item(saveFlower2).comment("테스트입니다.").rating(5).member(saveMember).build();
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);
        reviewRepository.save(review4);


        Pageable pageable = PageRequest.of(0, 10);

        Page<Review> reviews = reviewRepository.findByItem_Id(saveFlower1.getId(), pageable);

        log.info("reviews.size() = {}", reviews.getSize());
        log.info("reviews.getTotalElements() = {}", reviews.getTotalElements());



    }
    private Member getMember() {
        Member member = new Member("wnstjd5408@naver.com", "12345", "admin", "테스트", Gender.MAN, LocalDate.now(), Role.ADMIN, new Address("가", "나", "다"));
        memberRepository.save(member);
        return member;
    }

    private Item registerFLower(String name, int price, int stockQuantity){
        return Item.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .itemSellStatus(ItemSellStatus.SELL)
                .hitCount(0L)
                .itemImages(null)
                .delYn("Y")
                .build();
    }
}