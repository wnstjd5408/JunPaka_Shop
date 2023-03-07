package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pakaCoding.flower.repository.MemberRepository;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberDetailServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String memberName) throws UsernameNotFoundException {
        Long memberId = Long.valueOf(memberName);
        return memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException(String.format("member_id=%d", memberId)));
    }
}
