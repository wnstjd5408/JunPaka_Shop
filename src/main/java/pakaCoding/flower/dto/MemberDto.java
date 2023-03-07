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
    private Address address;
    private Role role;


    /* DTO -> Entity*/
    public Member toEntity(){
        Member member = Member.builder()
                .username(username)
                .password(password)
                .gender(gender)
                .address(address)
                .role(role)
                .build();

        return member;
    }
}
