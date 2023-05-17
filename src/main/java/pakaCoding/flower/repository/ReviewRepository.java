package pakaCoding.flower.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "select r from Review r join fetch r.member where r.flower.id = :flowerId",
    countQuery = "select count(r) from Review r where r.flower.id = :flowerId")
    Page<Review> findByFlower_Id(Long flowerId, Pageable pageable);
}
