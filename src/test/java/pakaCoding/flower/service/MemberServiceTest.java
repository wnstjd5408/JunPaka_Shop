package pakaCoding.flower.service;

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
class MemberServiceTest {


    @Autowired
    MemberRepository memberRepository;


    @DisplayName("insert time 확인")
    @Test
    void insertTime(){
        Member member = getMember();
        member.setEmail("ABC@naver.com");

        Member findMember = memberRepository.findById(member.getId()).orElseThrow(RuntimeException::new);

        //then
        Assertions.assertThat(findMember.getCreateDate()).isNotNull();
        Assertions.assertThat(findMember.getModifiedDate()).isNotNull();
    }



    private Member getMember() {
        Member member = new Member("wnstjd5408@naver.com", "12345", "admin", "테스트", Gender.MAN, LocalDate.now(), Role.ADMIN, new Address("가", "나", "다"));
        memberRepository.save(member);
        return member;
    }
}
