package pakaCoding.flower.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pakaCoding.flower.dto.BrandFormDto;
import pakaCoding.flower.service.BrandService;

import java.security.Principal;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BrandController {


    private final BrandService brandService;

    @GetMapping("/brand/create")
    public String newBrand(BrandFormDto brandFormDto, Model model){


        model.addAttribute("brandFormDto", brandFormDto);

        return "forms/brandForm";
    }

    @PostMapping("/brand/create")
    public String saveBrand(@Valid BrandFormDto brandFormDto,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            return "forms/brandForm";
        }

        log.info("Post : saveBrand 호출");
        try{
            Long brandId = brandService.saveBrand(brandFormDto);
            redirectAttributes.addAttribute("brandId", brandId);
            return "redirect:/brand/{brandId}";
        }catch (Exception e){
            model.addAttribute("errorMessage","브랜드 등록 중 에러가 발생했습니다.");
            return "forms/brandForm";
        }
    }




}
