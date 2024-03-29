package pakaCoding.flower.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.Cart;
import pakaCoding.flower.domain.entity.CartItem;
import pakaCoding.flower.domain.entity.Item;
import pakaCoding.flower.domain.entity.Member;
import pakaCoding.flower.dto.CartItemDto;
import pakaCoding.flower.dto.CartListDto;
import pakaCoding.flower.dto.CartOrderDto;
import pakaCoding.flower.dto.OrderDto;
import pakaCoding.flower.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;

    private final OrderService orderService;

    //장바구니 담기
    public Long addCart(CartItemDto cartItemDto, String userId){
        Cart cart = cartRepository.findByMemberUserid(userId);
        //장바구니가 존재하지 않는다면 생성
        if(cart == null){
            Member member = memberRepository.findByUserid(userId).orElseThrow(EntityNotFoundException::new);
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());


        //해당 상품이 장바구니에 존재하지 않는다면 생성 후 추가
        if(cartItem == null){
            cartItem =  CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
        }

        else{
            cartItem.addCount(cartItemDto.getCount());
        }
        return cartItem.getId();
    }


    //장바구니 조회
    @Transactional(readOnly = true)
    public List<CartListDto> getCartList(String userId){

        List<CartListDto> cartListDtos = new ArrayList<>();

        Cart cart = cartRepository.findByMemberUserid(userId);


        if (cart == null){
            return cartListDtos;
        }
        cartListDtos = cartItemRepository.findCartListDto(cart.getId());
        return cartListDtos;
    }

    @Transactional(readOnly = true)
    public List<CartListDto> findCartItemList(String userId){

        List<CartListDto> cartListDtos = new ArrayList<>();

        Cart cart = cartRepository.findByMemberUserid(userId);

        if (cart == null){
            return cartListDtos;
        }
        List<CartItem> cartItems = cartItemRepository.findCartItems(cart.getId());

        cartItems.forEach(cartItem -> {
            CartListDto cartListDto = CartListDto.builder()
                    .cartItemId(cartItem.getId())
                    .price(cartItem.getItem().getPrice())
                    .itemName(cartItem.getItem().getName())
                    .count(cartItem.getCount())
                    .imgUrl(cartItem.getItem().getItemImages().get(0).getSavedFileImgName())
                    .build();
            cartListDtos.add(cartListDto);
        });

        return cartListDtos;
    }


    @Transactional(readOnly = true)
    public Integer getCartListCount(String userId){

        Cart cart = cartRepository.findByMemberUserid(userId);

        if(cart == null){
            return 0;
        }

        Integer count = cartItemRepository.countByCartId(cart.getId());
        return count;

    }

    public void deleteCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    // 장바구니 상품 수량 변경
    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItem.update(count);
    }

    //장바구니 상품 주문
    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String userId){

        List<OrderDto> orderDtoList = new ArrayList<>();

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);
            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setCount(cartItem.getCount());
            orderDtoList.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtoList, userId);

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);

        }

        return orderId;
    }

    //로그인한 사용자가 장바구니 사용자가 동일한지 비교 확인
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String userId){

        Member member = memberRepository.findByUserid(userId).get();


        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember();

        if(member.getUserid().equals(savedMember.getUserid())){
            return true;
        }
        return false;
    }

}
