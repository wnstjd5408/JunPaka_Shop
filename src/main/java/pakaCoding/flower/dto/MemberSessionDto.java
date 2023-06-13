package pakaCoding.flower.dto;

import lombok.Data;
import lombok.Getter;
import pakaCoding.flower.domain.constant.Gender;
import pakaCoding.flower.domain.constant.Role;
import pakaCoding.flower.domain.entity.Address;
import pakaCoding.flower.domain.entity.Member;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class MemberSessionDto implements Serializable {

    private String userid;
    private String password;
    private String username;
    private Gender gender;
    private LocalDate birthDate;
    private Address address;
    private Role role;



    /* Entity -> Dto */
    public MemberSessionDto(Member member){
        this.userid = member.getUserid();
        this.password = member.getPassword();
        this.username = member.getUsername();
        this.gender = member.getGender();
        this.birthDate = member.getBirthDate();
        this.address = member.getAddress();
        this.role = member.getRole();
    }
}
