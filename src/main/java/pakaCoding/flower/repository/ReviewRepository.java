package pakaCoding.flower.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pakaCoding.flower.domain.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "select r from Review r join fetch r.member join r.reviewImages ri where r.item.id = :itemId",
    countQuery = "select count(r) from Review r where r.item.id = :itemId")
    Page<Review> findByItem_Id(Long itemId, Pageable pageable);
}
