package pakaCoding.flower.repository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pakaCoding.flower.domain.entity.Type;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class TypeRepositoryTest {


    @Autowired
    TypeRepository repository;


    @Test
    void findAll(){
        List<Type> all = repository.findAll();

        log.info("Type의 사이즈 {} 입니다",all.size() );
        Assertions.assertThat(all.size()).isEqualTo(3);
    }


}