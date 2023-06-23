package pakaCoding.flower.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.*;
import pakaCoding.flower.dto.ImageDto;
import pakaCoding.flower.dto.ReviewDto;
import pakaCoding.flower.dto.ReviewFormDto;
import pakaCoding.flower.repository.MemberRepository;
import pakaCoding.flower.repository.OrderItemRepository;
import pakaCoding.flower.repository.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ImageService itemImageService;
    private final OrderItemRepository orderItemRepository;

    public void saveReview(ReviewFormDto reviewFormDto, String userId){
        log.info("ReviewService에서 saveReview 실행");

        OrderItem orderItem = orderItemRepository.findById(reviewFormDto.getOrderItemId()).orElseThrow(EntityNotFoundException::new);
        Item item = orderItem.getItem();

        Member member = memberRepository.findByUserid(userId).orElseThrow(EntityNotFoundException::new);
        Review review = Review.createReview(member, item, orderItem, reviewFormDto.getComment(), reviewFormDto.getRating());
        reviewRepository.save(review);

        List<ImageDto> reviewFiles = itemImageService.saveImageFile(reviewFormDto.getMultipartFile());
        List<ReviewImage> reviewImages = reviewFiles.stream().
                        map(ImageDto::toEntityReviewImage).
                        collect(Collectors.toList());
        review.addReviewFiles(reviewImages);
    }

    @Transactional(readOnly = true)
    public Page<ReviewDto> findAllReview(Long flowerId ,int page){
        Pageable pageable = PageRequest.of(page, 10);
        log.info("ReviewService에 findAllReview 시작");

        Page<Review> reviews = reviewRepository.findByItem_Id(flowerId, pageable);


        List<ReviewDto> reviewDtos = reviews.stream()
                .map(review -> {
                    ReviewDto reviewDto = new ReviewDto(review);
                    List<ReviewImage> reviewImages = review.getReviewImages();
                    reviewImages.forEach(reviewImage -> {
                        reviewDto.addImgUrlList(reviewImage.getSavedFileImgName());
                    });
                    return reviewDto;
                }).toList();
        return new PageImpl<>(reviewDtos, pageable, reviews.getTotalElements());
    }
}
