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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.MemberSessionDto;
import pakaCoding.flower.dto.OrderDto;
import pakaCoding.flower.dto.OrderMyPageDto;
import pakaCoding.flower.service.CartService;
import pakaCoding.flower.service.OrderService;
import pakaCoding.flower.service.TypeService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final TypeService typeService;
    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderMyPage(Principal principal,
                            Model model,
                            @RequestParam(value = "page", defaultValue = "0") int page) {
        if (principal != null) {
            model.addAttribute("member", principal.getName());
            addCartCount(cartService.getCartListCount(principal.getName()), model);
        }
        else{
            model.addAttribute("cartCount", 0);
            return "redirect:/flowers";
        }

        log.info("GET : orderPage 실행");
        Page<OrderMyPageDto> orderList = orderService.getOrderList(principal.getName(), page);
        List<Type> types = typeService.allType();

        model.addAttribute("maxPage", 5);
        model.addAttribute("types", types);
        model.addAttribute("orders", orderList);

        return "order/orderMyPage";
    }



    //단일 상품 주문
    @PostMapping(value = "/order")
    @ResponseBody
    public ResponseEntity order(@RequestBody @Valid OrderDto orderDto,
                                BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        Long orderId;
        try{
            orderId = orderService.order(orderDto, principal.getName());
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(orderId);
    }

    //CartCount 추가
    private void addCartCount(Integer totalCartCount, Model model) {
        log.info("카트의 수 = {} ", totalCartCount);
        model.addAttribute("cartCount", totalCartCount);
    }

    //주문 취소
    @PostMapping(value = "/order/{orderId}/cancel")
    @ResponseBody
    public ResponseEntity orderCancel(@PathVariable(name = "orderId") Long orderId, Principal principal){
        orderService.orderCancel(orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
