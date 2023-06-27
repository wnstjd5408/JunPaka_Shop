package pakaCoding.flower.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pakaCoding.flower.domain.entity.Brand;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.BrandFormDto;
import pakaCoding.flower.dto.BrandItemListDto;
import pakaCoding.flower.dto.ImageDto;
import pakaCoding.flower.service.BrandService;
import pakaCoding.flower.service.CartService;
import pakaCoding.flower.service.ImageService;
import pakaCoding.flower.service.TypeService;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BrandController {


    private final BrandService brandService;
    private final CartService cartService;
    private final TypeService typeService;
    private final ImageService imageService;


    @GetMapping("/brands/create")
    public String newBrand(BrandFormDto brandFormDto, Model model){


        model.addAttribute("brandFormDto", brandFormDto);

        return "forms/brandForm";
    }

    @PostMapping("/brands/create")
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
            return "redirect:/brands/{brandId}";
        }catch (Exception e){
            model.addAttribute("errorMessage","브랜드 등록 중 에러가 발생했습니다.");
            return "forms/brandForm";
        }
    }

    @GetMapping("/brands/{brandId}")
    public String detailBrand(Principal principal,
                              @PathVariable long brandId,
                              Model model,
                              @RequestParam(value = "page", defaultValue = "0") int page){

        isPrincipal(principal, model);


        log.info("detailBrand 실행");

        Page<BrandItemListDto> brandItemList = brandService.getBrandItem(brandId, page);

        ImageDto imageDto = imageService.loadBrandImage(brandId);


        List<Type> types = typeService.allType();
        List<Brand> brands = brandService.findAll();

        model.addAttribute("maxPage", 5);
        model.addAttribute("types", types);
        model.addAttribute("brands", brands);
        model.addAttribute("brandName", brandItemList.getContent().get(0).getBrandName());
        model.addAttribute("brandItemList",brandItemList);
        model.addAttribute("brandImage", imageDto);

        return "brand/brandMain";
    }

    //CartCount 추가
    private void addCartCount(Integer count, Model model) {
        log.info("카트의 수 = {} ", count);
        model.addAttribute("cartCount", count);
    }


    private void isPrincipal(Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("member", principal.getName());
            addCartCount(cartService.getCartListCount(principal.getName()), model);
        }
        else{
            model.addAttribute("cartCount", 0);
        }
    }



}
