package pakaCoding.flower.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.ReviewFormDto;
import pakaCoding.flower.service.CartService;
import pakaCoding.flower.service.ReviewService;
import pakaCoding.flower.service.TypeService;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final TypeService typeService;
    private final CartService cartService;
    private final ReviewService reviewService;

    @GetMapping("/reviews/form")
    public String review(Principal principal, @RequestParam long orderItemNo, Model model){
        if(principal != null){
            model.addAttribute("member", principal.getName());
            addCartCount(cartService.getCartListCount(principal.getName()), model);
        }
        else{
            model.addAttribute("cartCount", 0);
        }
        List<Type> types = typeService.allType();

        model.addAttribute("orderItemNo", orderItemNo);
        model.addAttribute("types", types);
        model.addAttribute("reviewFormDto", new ReviewFormDto());

        return "forms/reviewForm";
    }

    @PostMapping("/reviews/form")
    public String review(@Valid ReviewFormDto reviewFormDto,
                         BindingResult bindingResult,
                         Principal principal,
                         Model model){
        List<Type> types = typeService.allType();

        if(bindingResult.hasErrors()){
            model.addAttribute("types", types);
            return "forms/reviewForm";
        }

        log.info("Post : review 호출");

        reviewService.saveReview(reviewFormDto, principal.getName());


        return "redirect:/";
    }

    //CartCount 추가
    private void addCartCount(Integer cartService, Model model) {
        Integer count = cartService;
        log.info("카트의 수 = {} ", count);
        model.addAttribute("cartCount", count);
    }

}
