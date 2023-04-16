package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pakaCoding.flower.domain.constant.Gender;
import pakaCoding.flower.domain.constant.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Member extends TimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Email(message = "이메일 형식으로 입력해주세요")
    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;

    @Length(min=5, message = "최소 5글자 이상 입력해주세요")
    @NotEmpty(message = "*비밀번호를 다시 입력해주세요")
    private String password;

    @NotNull
    private String username;

    @NotNull
    private String userid;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender; //성별 [남, 녀]

    @Column(name="birth_date")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @NotNull
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Embedded // Embedded 또는 Embeddable(클래스에) 둘 중 하나의 어노테이션만 있어도 되긴 한다.
    private Address address;


    @OneToMany(mappedBy = "member") //order 객체에 있는 member 필드에 의해서 매핑
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Member(String email, String password, String userid, String username, Gender gender, LocalDate birthDate, Role role, Address address) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.userid = userid;
        this.gender = gender;
        this.birthDate = birthDate;
        this.role = role;
        this.address = address;
    }
}
