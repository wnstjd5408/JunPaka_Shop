package pakaCoding.flower.dto;

import jakarta.validation.constraints.*;
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

    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
    private String userid;
  
  
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{5,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")

    private String password;

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{2,5}$", message = "이름은 한글로 2~5자리여야 합니다.")
    private String username;
    
    @NotNull(message = "성별을 입력해주세요")
    private Gender gender;
    
    @NotNull(message = "날짜를 입력해주세요")
    private LocalDate birthDate;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email
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
