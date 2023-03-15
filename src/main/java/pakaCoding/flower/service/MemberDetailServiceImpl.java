package pakaCoding.flower.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pakaCoding.flower.domain.entity.CustomUserDetails;
import pakaCoding.flower.domain.entity.Member;
import pakaCoding.flower.dto.MemberSessionDto;
import pakaCoding.flower.repository.MemberRepository;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberDetailServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final HttpSession session;

    // memberName이 DB에 있는지 확인
    @Override
    public UserDetails loadUserByUsername(String memberName) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(memberName).orElseThrow(() ->
                new UsernameNotFoundException("해당 사용자가 존재하지 않습니다 : " + memberName));
        session.setAttribute("member", new MemberSessionDto(member));

        // 시큐리티 세션에 유저 정보 저장
        return new CustomUserDetails(member);
    }
}
