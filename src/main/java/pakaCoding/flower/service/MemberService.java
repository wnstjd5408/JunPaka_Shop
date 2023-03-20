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
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder encoder;


    @Transactional
    public Long join(MemberDto dto){

        dto.setPassword(encoder.encode(dto.getPassword()));

        return memberRepository.save(dto.toEntity()).getId();

    }
}
