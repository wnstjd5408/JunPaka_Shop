package pakaCoding.flower.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pakaCoding.flower.config.auth.CustomAccessDeniedHandler;
import pakaCoding.flower.config.auth.CustomAuthFailureHandler;
import pakaCoding.flower.config.auth.CustomAuthenticationEntryPoint;
import pakaCoding.flower.service.CustomUserDetailService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomUserDetailService customUserDetailService;

    private final DataSource dataSource;




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws  Exception{
        httpSecurity
                .authorizeHttpRequests()
                    .requestMatchers( "/logout", "/orders/**").authenticated()
                    .requestMatchers("/admin/items/**", "/brands/create").hasRole("ADMIN")
                    .requestMatchers("/order", "/reviews/form/**").hasAnyRole("ADMIN", "USER")
                    .requestMatchers("/members/**").anonymous() //인증되지 않은 사용자만 접근허용
                    .anyRequest().permitAll()
                    .and()
                .formLogin()
                    .loginPage("/members/login")
                    .loginProcessingUrl("/loginProc")
                    .failureHandler(customAuthFailureHandler())
                    .defaultSuccessUrl("/")
                    .and()
                .logout()
                    .logoutSuccessUrl("/") // 로그아웃 성공시 리턴 URL
                    .invalidateHttpSession(true) // 로그아웃 시 세션 종료
                    .clearAuthentication(true) //로그아웃 시 권한 제거
                    .deleteCookies("JSESSIONID") // 로그아웃 시 JESSIONID 제거
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
                    .and()
                .rememberMe()
                    .tokenRepository(tokenRepository())
                    .userDetailsService(customUserDetailService)
                    .rememberMeParameter("remember-me");
        return httpSecurity.build();
    }

    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthFailureHandler customAuthFailureHandler(){
        return new CustomAuthFailureHandler();
    }
}
