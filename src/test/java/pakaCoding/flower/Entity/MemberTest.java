package pakaCoding.flower.Entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.constant.Gender;
import pakaCoding.flower.domain.constant.Role;
import pakaCoding.flower.domain.entity.Address;
import pakaCoding.flower.domain.entity.Member;
import pakaCoding.flower.repository.MemberRepository;

import java.time.LocalDate;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;


    @DisplayName("insert time 확인")
    @Test
    void insertTime(){
        Member member = getMember();
        member.setEmail("ABC@naver.com");

        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member.getId()).orElseThrow(EntityNotFoundException::new);

        System.out.println("register time : " + member.getCreateDate());
        System.out.println("ModifiedDate time : " + member.getModifiedDate());
        System.out.println("Create Member : " + member.getCreatedBy());
        System.out.println("update Member : " + member.getModifiedBy());
    }

    private Member getMember() {
        Member member = new Member("wnstjd5408@naver.com", "12345", "admin", "테스트", Gender.MAN, LocalDate.now(), Role.ADMIN, new Address("가", "나", "다"));
        memberRepository.save(member);
        return member;
    }
}
