package pakaCoding.flower.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pakaCoding.flower.config.auth.CustomAccessDeniedHandler;
import pakaCoding.flower.config.auth.CustomAuthFailureHandler;
import pakaCoding.flower.config.auth.CustomAuthenticationEntryPoint;
import pakaCoding.flower.domain.constant.Role;
import pakaCoding.flower.service.MemberDetailServiceImpl;

import java.beans.Encoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws  Exception{
        httpSecurity
                .authorizeHttpRequests()
                    .requestMatchers( "/logout", "/orders/**", "/reviews/**").authenticated()
                    .requestMatchers("/admin/flowers/**").hasRole("ADMIN")
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
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                    .accessDeniedHandler(new CustomAccessDeniedHandler());

        return httpSecurity.build();
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
