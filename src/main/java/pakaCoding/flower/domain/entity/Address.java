package pakaCoding.flower.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@Getter
@AllArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;


    protected Address() {  // jpa spec에 맞춰주기 위해 기본생성자를 만든다. private이 허용이  안되서 protected 선언
    }
}

