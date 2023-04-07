package pakaCoding.flower.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import pakaCoding.flower.domain.entity.Address;
import pakaCoding.flower.domain.entity.Member;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.MemberDto;
import pakaCoding.flower.dto.MemberFormDto;
import pakaCoding.flower.dto.MemberSessionDto;
import pakaCoding.flower.service.MemberService;
import pakaCoding.flower.service.TypeService;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {


    private final TypeService typeService;
    private final MemberService memberService;

    @GetMapping("/members/login")
    public String login(@RequestParam(value="error", required = false)String error,
                        @RequestParam(value="exception", required = false)String exception,
                        Model model){

        List<Type> types = typeService.allType();
        /* 에러와 예외를 모델에 담아 view resolve */
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        model.addAttribute("types", types);
        model.addAttribute("memberFormDto", new MemberFormDto());

        return "forms/loginForm";
    }

    @GetMapping("/members/join")
    public String join(Model model){



        List<Type> types = typeService.allType();
        LocalDate now = LocalDate.now();

        model.addAttribute("memberDto", new MemberDto());
        model.addAttribute("types", types);
        model.addAttribute("now", now);

        return "forms/joinForm";

    }
    @PostMapping("/members/join")
    public String join(@Valid MemberDto memberDto,
                       BindingResult bindingResult, Model model){
        List<Type> types = typeService.allType();
        LocalDate now = LocalDate.now();

        if(bindingResult.hasErrors()){
            model.addAttribute("types", types);
            model.addAttribute("now", now);
            return "forms/joinForm";
        }

        log.info("MemberController 실행");

        memberService.join(memberDto);

        return "redirect:/members/login";

    }

}
