package pakaCoding.flower.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import pakaCoding.flower.domain.constant.Gender;
import pakaCoding.flower.domain.constant.Role;
import pakaCoding.flower.domain.entity.Address;
import pakaCoding.flower.domain.entity.Cart;
import pakaCoding.flower.domain.entity.Member;

import java.time.LocalDate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@Slf4j
class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;



    @PersistenceContext
    EntityManager em;


    private Member getMember() {
        Member member = new Member("wnstjd5408@naver.com", "12345", "admin",
                "테스트", Gender.MAN, LocalDate.now(), Role.ADMIN, new Address("가", "나", "다"), "010-2950-1671");
        memberRepository.save(member);
        return member;
    }

    @Test
    public void save(){


        Member member = getMember();
        Cart cart = Cart.createCart(member);

        Cart findCart = cartRepository.findByMemberUserid(member.getUserid());

        if(findCart == null) {
            log.info("findCart = {}", findCart);
        }
//        Assertions.assertThat(findCart.getMember().getId()).isNotEqualTo(cart.getMember().getId());
    }
}