package pakaCoding.flower.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import pakaCoding.flower.domain.constant.Gender;
import pakaCoding.flower.domain.constant.Role;
import pakaCoding.flower.domain.entity.Address;
import pakaCoding.flower.domain.entity.Member;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    @NotBlank(message = "아이디를 입력해주세요")
    private String userid;

    @Length(min=5, message = "최소 5글자 이상 입력해주세요")
    @NotEmpty(message = "*비밀번호를 다시 입력해주세요")
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    private String username;
    private Gender gender;
    
    @NotNull(message = "날짜를 입력해주세요")
    private LocalDate birthDate;

    @Email(message = "이메일 형식으로 입력해주세요")
    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;
    private String zipcode;
    private String streetAdr;
    private String detailAdr;
    private Role role;


    /* DTO -> Entity*/
    public Member toEntity(){

        return Member.builder()
                .userid(userid)
                .password(password)
                .username(username)
                .gender(gender)
                .birthDate(birthDate)
                .address(new Address(streetAdr, detailAdr, zipcode))
                .email(email)
                .role(role.USER)
                .build();
    }
}
