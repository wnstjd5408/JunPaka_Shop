package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pakaCoding.flower.domain.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
