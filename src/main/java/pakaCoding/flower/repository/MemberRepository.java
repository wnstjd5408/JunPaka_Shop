package pakaCoding.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pakaCoding.flower.domain.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserid(String userid);

    @Query("select m.id from Member m where m.userid = :userid")
    Long getMemberId(String userid);

    /* 유효성 검사 - 중복 체크
     * 중복 : true
     * 중복이 아닌 경우 : false
     */
    boolean existsByUserid(String userId);

    boolean existsByEmail(String email);
}
