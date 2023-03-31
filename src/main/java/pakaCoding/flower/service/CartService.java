package pakaCoding.flower.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.Cart;
import pakaCoding.flower.domain.entity.CartItem;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.Member;
import pakaCoding.flower.dto.CartItemDto;
import pakaCoding.flower.repository.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final FlowerRepository flowerRepository;
    private final CartItemRepository cartItemRepository;
    private final TypeRepository typeRepository;

    private final OrderService orderService;

    //장바구니 담기
    @Transactional
    public Long addCart(CartItemDto cartItemDto, String userId){
        Member member = memberRepository.findByUserid(userId).get();
        Cart cart = cartRepository.findByMemberId(member.getId());
        //장바구니가 존재하지 않는다면 생성
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }
        log.info("cart.getMember().getName() ={}", cart.getMember().getUsername());
        Flower flower = flowerRepository.findById(cartItemDto.getFlowerId())
                .orElseThrow(EntityNotFoundException::new);
        CartItem cartItem = cartItemRepository.findByCartIdAndFlowerId(cart.getId(), flower.getId());


        //해당 상품이 장바구니에 존재하지 않는다면 생성 후 추가
        if(cartItem == null){
            cartItem =  CartItem.createCartItem(cart, flower, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
        }

        else{
            cartItem.addCount(cartItemDto.getCount());
        }
        return cartItem.getId();
    }
}
