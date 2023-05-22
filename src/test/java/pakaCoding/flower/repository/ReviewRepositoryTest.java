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
import pakaCoding.flower.domain.constant.FlowerSellStatus;
import pakaCoding.flower.domain.constant.Gender;
import pakaCoding.flower.domain.constant.Role;
import pakaCoding.flower.domain.entity.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
@Slf4j
class ReviewRepositoryTest {


    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FlowerRepository flowerRepository;

    @Test
    @DisplayName("flowerId로 검색")
    public void findFlowerID(){
        Member saveMember = getMember();
        Flower saveFlower1 = flowerRepository.save(registerFLower("테스트꽃1", 10000, 10));
        Flower saveFlower2 = flowerRepository.save(registerFLower("테스트꽃2", 10000, 10));
        Flower saveFlower3 = flowerRepository.save(registerFLower("테스트꽃3", 10000, 10));

        Review review1 = Review.builder().flower(saveFlower1).comment("테스트입니다.").rating(5).member(saveMember).build();
        Review review2 = Review.builder().flower(saveFlower1).comment("테스트입니다.").rating(5).member(saveMember).build();
        Review review3 = Review.builder().flower(saveFlower1).comment("테스트입니다.").rating(5).member(saveMember).build();
        Review review4 = Review.builder().flower(saveFlower2).comment("테스트입니다.").rating(5).member(saveMember).build();
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);
        reviewRepository.save(review4);


        Pageable pageable = PageRequest.of(0, 10);

        Page<Review> reviews = reviewRepository.findByFlower_Id(saveFlower1.getId(), pageable);

        log.info("reviews.size() = {}", reviews.getSize());
        log.info("reviews.getTotalElements() = {}", reviews.getTotalElements());



    }
    private Member getMember() {
        Member member = new Member("wnstjd5408@naver.com", "12345", "admin", "테스트", Gender.MAN, LocalDate.now(), Role.ADMIN, new Address("가", "나", "다"));
        memberRepository.save(member);
        return member;
    }

    private Flower registerFLower(String name, int price, int stockQuantity){
        return Flower.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .flowerSellStatus(FlowerSellStatus.SELL)
                .hitCount(0L)
                .fileImages(null)
                .delYn("Y")
                .build();
    }
}