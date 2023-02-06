package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pakaCoding.flower.domain.entity.Type;

public interface TypeRepository extends JpaRepository<Type, Integer> {

}
