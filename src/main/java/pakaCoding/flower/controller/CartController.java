package pakaCoding.flower.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pakaCoding.flower.dto.CartItemDto;
import pakaCoding.flower.dto.CartListDto;
import pakaCoding.flower.service.CartService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {


    private final CartService cartService;

    //장바구니 조회
    @GetMapping("/cart")
    public String cartList(Principal principal, Model model){
        if (principal != null){
            List<CartListDto> cartListDtos = cartService.getCartList(principal.getName());
            model.addAttribute("member", principal.getName());
            model.addAttribute("cartItems", cartListDtos);
        }
        return "cart/cartList";
    }


    //장바구니 담기
    @PostMapping("/cart")
    @ResponseBody
    public ResponseEntity cart(@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult,
                               Principal principal){

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        Long cartItemId;


        try {
            log.info("principal.getName()의 이름 : {}", principal.getName());
            log.info("cartItemDto 개수 = {}", cartItemDto.getCount());
            cartItemId = cartService.addCart(cartItemDto, principal.getName());
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    //장바구니 삭제
    @DeleteMapping(value = "/cartItem/{cartItemId}")
    @ResponseBody
    public ResponseEntity deleteCartItem(@PathVariable(value = "cartItemId") Long cartItemId){
        cartService.deleteCartItem(cartItemId);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }
}
