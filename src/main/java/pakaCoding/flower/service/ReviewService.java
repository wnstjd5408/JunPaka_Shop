package pakaCoding.flower.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.Member;
import pakaCoding.flower.domain.entity.OrderItem;
import pakaCoding.flower.domain.entity.Review;
import pakaCoding.flower.dto.ReviewFormDto;
import pakaCoding.flower.repository.MemberRepository;
import pakaCoding.flower.repository.OrderItemRepository;
import pakaCoding.flower.repository.ReviewRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final OrderItemRepository orderItemRepository;

    public Long saveReview(ReviewFormDto reviewFormDto, String userId){
        log.info("ReviewService에서 saveReview 실행");

        OrderItem orderItem = orderItemRepository.findById(reviewFormDto.getOrderItemId()).orElseThrow(EntityNotFoundException::new);
        Flower flower = orderItem.getFlower();

        Member member = memberRepository.findByUserid(userId).orElseThrow(EntityNotFoundException::new);
        Review review = Review.createReview(member, flower, orderItem, reviewFormDto.getComment(), reviewFormDto.getRating());
        Review findReview = reviewRepository.save(review);


        return findReview.getId();

    }
}
