package pakaCoding.flower.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.CustomUserDetails;
import pakaCoding.flower.domain.entity.Member;
import pakaCoding.flower.dto.MemberSessionDto;
import pakaCoding.flower.repository.MemberRepository;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
//    private final HttpSession session;

    // memberName이 DB에 있는지 확인
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // MemberRepository를 통해 username에 해당하는 member의 존재여부만 판단
        Member member = memberRepository.findByUserid(username).orElseThrow(() ->
                new UsernameNotFoundException("해당 사용자가 존재하지 않습니다 : " + username));
//        session.setAttribute("member", new MemberSessionDto(member));

        // 시큐리티 세션에 유저 정보 저장
        return new CustomUserDetails(member);
    }
}
