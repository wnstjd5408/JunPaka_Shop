package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pakaCoding.flower.domain.entity.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

}
