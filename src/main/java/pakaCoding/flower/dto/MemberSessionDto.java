package pakaCoding.flower.dto;

import lombok.Getter;
import pakaCoding.flower.domain.constant.Gender;
import pakaCoding.flower.domain.constant.Role;
import pakaCoding.flower.domain.entity.Address;
import pakaCoding.flower.domain.entity.Member;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class MemberSessionDto implements Serializable {

    private String username;
    private String password;
    private Gender gender;
    private LocalDate birthDate;
    private Address address;
    private Role role;



    /* Entity -> Dto */
    public MemberSessionDto(Member member){
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.gender = member.getGender();
        this.birthDate = member.getBirthDate();
        this.address = member.getAddress();
        this.role = member.getRole();
    }
}
