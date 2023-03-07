package pakaCoding.flower.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.MemberFormDto;
import pakaCoding.flower.service.TypeService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {


    private final TypeService typeService;

    @GetMapping("/members/create")
    public String newMember(Model model){
        List<Type> types = typeService.allType();

        model.addAttribute("memberFormDto", new MemberFormDto());
        model.addAttribute("types", types);

        return "forms/loginForm";
    }
}
