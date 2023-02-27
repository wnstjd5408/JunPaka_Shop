package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import pakaCoding.flower.domain.constant.Gender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member extends TimeEntity {


    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Email(message = "이메일 형식으로 입력해주세요")
    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;

    @Length(min=5, message = "최소 5글자 이상 입력해주세요")
    @NotEmpty(message = "*비밀번호를 다시 입력해주세요")
    private String password;

    @NotNull
    private String username;

    @Enumerated
    @NotNull
    private Gender gender; //성별 [남, 녀]

    @Column(name="birth_date")
    @NotNull
    private LocalDate birthDate;

    @Embedded // Embedded 또는 Embeddable(클래스에) 둘 중 하나의 어노테이션만 있어도 되긴 한다.
    private Address address;

    @Column(name="is_active")
    private int active;

    @Column(name="is_admin")
    private int admin;


    @OneToMany(mappedBy = "member") //order 객체에 있는 member 필드에 의해서 매핑
    private List<Order> orders = new ArrayList<>();
}
