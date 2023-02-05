package pakaCoding.flower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication //Base_Entity에 있는 날짜 자동 입력 활성화
@EnableJpaAuditing
public class FlowerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowerApplication.class, args);
	}

}
