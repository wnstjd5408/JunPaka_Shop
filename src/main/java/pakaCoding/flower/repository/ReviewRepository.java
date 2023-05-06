package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pakaCoding.flower.domain.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
