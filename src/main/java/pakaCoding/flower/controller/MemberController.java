package pakaCoding.flower.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String login(@SessionAttribute(name = "member", required = false) MemberSessionDto loginMember,
                        Model model){

        List<Type> types = typeService.allType();
        model.addAttribute("types", types);

        if (loginMember !=null){
            return "flowers/flowerList";
        }



        model.addAttribute("memberFormDto", new MemberFormDto());

        return "forms/loginForm";
    }

    @GetMapping("/members/join")
    public String join(@SessionAttribute(name = "member", required = false)Member loginMember,Model model){

        if (loginMember != null){
            return "flowers/flowerList";
        }

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

        if(bindingResult.hasErrors()){
            model.addAttribute("types", types);
            return "forms/flowerForm";
        }

        log.info("MemberController 실행");

        memberService.join(memberDto);

        return "redirect:/members/login";

    }

}
