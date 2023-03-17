package pakaCoding.flower.dto;

import lombok.*;
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


    private String username;
    private String password;
    private Gender gender;
    private LocalDate birthDate;
    private String email;
    private String zipcode;
    private String streetAdr;
    private String detailAdr;
    private Role role;


    /* DTO -> Entity*/
    public Member toEntity(){
        Member member = Member.builder()
                .username(username)
                .password(password)
                .gender(gender)
                .birthDate(birthDate)
                .address(new Address(streetAdr, detailAdr, zipcode))
                .email(email)
                .role(role.USER)
                .build();

        return member;
    }
}
