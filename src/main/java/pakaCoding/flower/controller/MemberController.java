package pakaCoding.flower.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pakaCoding.flower.controller.validator.CheckEmailValidator;
import pakaCoding.flower.domain.entity.Address;
import pakaCoding.flower.domain.entity.Brand;
import pakaCoding.flower.domain.entity.Member;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.MemberDto;
import pakaCoding.flower.dto.MemberFormDto;
import pakaCoding.flower.dto.MemberSessionDto;
import pakaCoding.flower.service.BrandService;
import pakaCoding.flower.service.MemberService;
import pakaCoding.flower.service.TypeService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {


    private final TypeService typeService;
    private final MemberService memberService;
    private final BrandService brandService;

    private final CheckEmailValidator checkEmailValidator;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {

        binder.addValidators(checkEmailValidator);
    }


    @GetMapping("/members/login")
    public String login(@RequestParam(value="error", required = false)String error,
                        @RequestParam(value="exception", required = false)String exception,
                        Model model){

        List<Type> types = typeService.allType();
        List<Brand> brands = brandService.findAll();
        /* 에러와 예외를 모델에 담아 view resolve */
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        model.addAttribute("types", types);
        model.addAttribute("brands", brands);
        model.addAttribute("memberFormDto", new MemberFormDto());

        return "forms/loginForm";
    }

    @GetMapping("/members/join")
    public String join(Model model){

        List<Type> types = typeService.allType();
        List<Brand> brands = brandService.findAll();

        LocalDate now = LocalDate.now();

        model.addAttribute("memberDto", new MemberDto());
        model.addAttribute("types", types);
        model.addAttribute("brands", brands);
        model.addAttribute("now", now);

        return "forms/joinForm";

    }
    @PostMapping("/members/join")
    public String join(@Valid MemberDto memberDto,
                       BindingResult bindingResult, Model model){

        LocalDate now = LocalDate.now();

        if(bindingResult.hasErrors()){
            /* 회원가입 실패 시 입력 데이터 값 유지 */
            model.addAttribute("memberDto", memberDto);

            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
                log.info("error message : " + error.getDefaultMessage());
            }

            List<Type> types = typeService.allType();
            List<Brand> brands = brandService.findAll();

            model.addAttribute("types", types);
            model.addAttribute("brands", brands);
            model.addAttribute("now", now);
            /* 회원가입 페이지로 리턴 */
            return "forms/joinForm";
        }

        // 회원가입 성공 시
        memberService.join(memberDto);

        return "redirect:/members/login";

    }
    @ResponseBody
    @GetMapping(value ="/members/{userId}/IdCheck")
    public ResponseEntity<Boolean> checkUserIDDuplicate(@PathVariable("userId") String userId){

        log.info("Service checkUserIDDuplicate 실행");
        return ResponseEntity.ok(memberService.checkUserIdDuplication(userId));
    }


}
