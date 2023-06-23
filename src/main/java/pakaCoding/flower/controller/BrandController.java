package pakaCoding.flower.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pakaCoding.flower.dto.BrandFormDto;

import java.security.Principal;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BrandController {


    @GetMapping("/brand/create")
    public String newBrand(BrandFormDto brandFormDto, Model model){


        model.addAttribute("brandFormDto", brandFormDto);

        return "forms/brandForm";
    }

}
