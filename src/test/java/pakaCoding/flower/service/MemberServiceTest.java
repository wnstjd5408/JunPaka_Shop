package pakaCoding.flower.service;

import jakarta.persistence.EntityExistsException;
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
import pakaCoding.flower.domain.entity.Brand;
import pakaCoding.flower.domain.entity.Member;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.MemberDto;
import pakaCoding.flower.repository.BrandRepository;
import pakaCoding.flower.repository.MemberRepository;
import pakaCoding.flower.repository.TypeRepository;

import java.time.LocalDate;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class MemberServiceTest {


    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    @Test
    @DisplayName("Join Test")
    void joinTest(){

        MemberDto newMember = MemberDto.builder().birthDate(LocalDate.of(1997, 7, 19))
                .email("abcd@gmail.com")
                .detailAdr("나")
                .streetAdr("가")
                .zipcode("다")
                .userid("test")
                .password("1234")
                .username("테스트")
                .gender(Gender.MAN)
                .phoneNumber("01029501671")
                .build();


        Long joinId = memberService.join(newMember);


        Member findMember = memberRepository.findById(joinId).orElseThrow(EntityExistsException::new);


        System.out.println("findMember.getPhoneNumber :" + findMember.getPhoneNumber());


    }
}
