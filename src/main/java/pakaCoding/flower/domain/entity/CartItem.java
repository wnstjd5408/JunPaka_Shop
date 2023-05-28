package pakaCoding.flower.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "cart_item")
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="flower_id")
    private Flower flower;

    @NotNull
    private int count;

    public static CartItem createCartItem(Cart cart, Flower flower, int count){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setFlower(flower);
        cartItem.setCount(count);
        return cartItem;
    }
    //장바구니 숫자 증가
    public void addCount(int count){
        this.count += count;
    }

    //장바구니 숫자 업데이트(감소)
    public void update(int count){
        this.count = count;
    }


}
