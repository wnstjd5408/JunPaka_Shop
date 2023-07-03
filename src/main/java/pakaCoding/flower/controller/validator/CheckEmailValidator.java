package pakaCoding.flower.controller.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import pakaCoding.flower.dto.MemberDto;
import pakaCoding.flower.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class CheckEmailValidator extends AbstractValidator<MemberDto> {


    private final MemberRepository memberRepository;


    @Override
    protected void doValidate(MemberDto dto, Errors errors) {
        if(memberRepository.existsByEmail(dto.toEntity().getEmail()))
            errors.rejectValue("email", "이메일 중복 오류", "이미 사용 중인 이메일입니다. ");
    }
}
