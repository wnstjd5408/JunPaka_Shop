package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.Address;
import pakaCoding.flower.dto.MemberDto;
import pakaCoding.flower.repository.MemberRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService{

    private final MemberRepository memberRepository;

    private final PasswordEncoder encoder;


    @Transactional
    public Long join(MemberDto dto){

        dto.setPassword(encoder.encode(dto.getPassword()));

        String pn = dto.getPhoneNumber();
        String phoneNumberPlusBar = pn.substring(0,3) + "-" +  pn.substring(3,7) + "-" + pn.substring(7,11);
        log.info("phoneNumber = {}", phoneNumberPlusBar);

        dto.setPhoneNumber(phoneNumberPlusBar);

        log.info("DB에 회원 저장 성공");
        return memberRepository.save(dto.toEntity()).getId();

    }

    @Transactional(readOnly = true)
    public boolean checkUserIdDuplication(String userId){
        return memberRepository.existsByUserid(userId);
    }


    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(String email){
        return memberRepository.existsByEmail(email);
    }
}
