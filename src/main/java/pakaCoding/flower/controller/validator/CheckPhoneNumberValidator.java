package pakaCoding.flower.controller.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import pakaCoding.flower.dto.MemberDto;
import pakaCoding.flower.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class CheckPhoneNumberValidator extends AbstractValidator<MemberDto>{


    private final MemberRepository memberRepository;
    @Override
    protected void doValidate(MemberDto dto, Errors errors) {
        if(memberRepository.existsByPhoneNumber(dto.toEntity().getPhoneNumber())){
            errors.rejectValue("phoneNumber", "핸드폰 중복 오류", "사용중인 핸드폰 번호 입니다");
        }

    }
}
