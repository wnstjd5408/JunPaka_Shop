package pakaCoding.flower.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pakaCoding.flower.domain.constant.Gender;
import pakaCoding.flower.domain.constant.Role;
import pakaCoding.flower.domain.entity.Member;
import pakaCoding.flower.dto.MemberDto;
import pakaCoding.flower.service.MemberService;

import java.time.LocalDate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class MemberRepositoryTest {




    @Autowired
    MemberRepository memberRepository;


    @Test
    void joinTest(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        LocalDate birth = LocalDate.of(1997, 07, 19);
        MemberDto member = MemberDto.builder()
                .birthDate(birth)
                .password("1234")
                .gender(Gender.MAN)
                .role(Role.ADMIN)
                .username("ID")
                .address(null)
                .email("wnstjd5408@naver.com")
                .build();

        member.setPassword(encoder.encode(member.getPassword()));

        Member saveMember = memberRepository.save(member.toEntity());

        Assertions.assertThat(member.getPassword()).isEqualTo(saveMember.getUsername());


    }


}