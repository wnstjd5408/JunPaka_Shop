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
import pakaCoding.flower.domain.entity.Brand;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.CartItemDto;
import pakaCoding.flower.dto.CartListDto;
import pakaCoding.flower.dto.CartOrderDto;
import pakaCoding.flower.service.BrandService;
import pakaCoding.flower.service.CartService;
import pakaCoding.flower.service.TypeService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {


    private final CartService cartService;
    private final BrandService brandService;
    private final TypeService typeService;

    //장바구니 조회
    @GetMapping("/cart")
    public String cartList(Principal principal, Model model){
        if (principal != null){
            List<Type> types = typeService.allType();
            List<Brand> brands = brandService.findAll();

            model.addAttribute("types", types);
            model.addAttribute("brands", brands);

            List<CartListDto> cartListDtos = cartService.getCartList(principal.getName());
            log.info("carListDtos의 사이즈 = {}", cartListDtos.size());
            model.addAttribute("member", principal.getName());
            if (cartListDtos.size() != 0){
                model.addAttribute("cartItems", cartListDtos);
            }
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

    //장바구니 상품(한개 또는 여러개) 주문
    @PostMapping(value = "/cart/orders")
    @ResponseBody
    public ResponseEntity orders(@RequestBody CartOrderDto cartOrderDto, Principal principal) {

        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if(cartOrderDtoList == null || cartOrderDtoList.size() == 0){
            return new ResponseEntity<String>("주문할 상품을 선택해주세요.", HttpStatus.BAD_REQUEST);
        }

        Long orderId = cartService.orderCartItem(cartOrderDtoList, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    // 장바구니 수량 변경
    @PatchMapping(value = "/cartItem/{cartItemId}")
    @ResponseBody
    public ResponseEntity updateCartItem(@PathVariable(value = "cartItemId") Long cartItemId, int count,
                                         Principal principal){

        if(count < 1){
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        } else if(!cartService.validateCartItem(cartItemId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        cartService.updateCartItemCount(cartItemId, count);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }


    //장바구니 삭제
    @DeleteMapping(value = "/cartItem/{cartItemId}")
    @ResponseBody
    public ResponseEntity deleteCartItem(@PathVariable(value = "cartItemId") Long cartItemId,Principal principal){
        if(!cartService.validateCartItem(cartItemId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.deleteCartItem(cartItemId);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }
}
