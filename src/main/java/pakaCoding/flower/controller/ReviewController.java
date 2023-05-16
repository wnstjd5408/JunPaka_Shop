package pakaCoding.flower.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.OrderMyPageDto;
import pakaCoding.flower.dto.ReviewDto;
import pakaCoding.flower.dto.ReviewFormDto;
import pakaCoding.flower.service.CartService;
import pakaCoding.flower.service.OrderService;
import pakaCoding.flower.service.ReviewService;
import pakaCoding.flower.service.TypeService;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final TypeService typeService;
    private final CartService cartService;
    private final ReviewService reviewService;
    private final OrderService orderService;

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
    public String saveReview(@Valid ReviewFormDto reviewFormDto,
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

        return "redirect:/orders";
    }
    @GetMapping(value = {"/reviews/{flowerId}"})
    @ResponseBody
    public DeferredResult<ResponseEntity<Page<ReviewDto>>> showReview(@PathVariable("flowerId") Long flowerId,
                                                                      @RequestParam(value="page", defaultValue = "0") int page){
        //비동기로 처리할 작업을 수행합니다.
        DeferredResult<ResponseEntity<Page<ReviewDto>>> deferredResult = new DeferredResult<>();

        //비동기 작업을 시작하고, 작업이 완료되면 결과를 deferredResult에 설정합니다.
        CompletableFuture.supplyAsync(() -> {
            // 비동기로 처리할 작업 내용을 작성합니다.
            // 페이지 정보를 이용하여 데이터를 조회합니다.
            Page<ReviewDto> allReview = reviewService.findAllReview(flowerId, page);
            return ResponseEntity.ok(allReview);
        }).whenComplete((result, throwable) ->{

            if (throwable != null) {

                //작업 중에 예외가 발생한 경우 예외 처리를 수행합니다.
                deferredResult.setErrorResult(throwable);
            }else{
                //작업이 정상적으로 완료된 경우 결과를 deferredResult에 설정합니다.
                deferredResult.setResult(result);
            }
        });

        return deferredResult;
    }
    //CartCount 추가
    private void addCartCount(Integer cartService, Model model) {
        Integer count = cartService;
        log.info("카트의 수 = {} ", count);
        model.addAttribute("cartCount", count);
    }

}
