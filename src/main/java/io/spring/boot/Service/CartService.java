package io.spring.boot.Service;

import io.spring.boot.Entity.CartItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CartService {
    Map<Long,CartItem> maps = new HashMap<>(); // giỏ hàng


    public void addItemToCart(CartItem item) {
      CartItem cartItem = maps.get(item.getProduct().getId());

      if(cartItem == null){
          maps.put(item.getProduct().getId(),item);
      }else {
          cartItem.setQuantity(cartItem.getQuantity()+1);
      }

    }

    public Collection<CartItem> getListCart(){
        return maps.values();
    }


    public void remove(Long id){
        maps.remove(id);
    }


    public CartItem update(Long proId, Integer quantity){
        CartItem cartItem = maps.get(proId);
        cartItem.setQuantity(quantity);

        return cartItem;
    }

    public void clear(){
        maps.clear();
    }

    public Integer getCount() {
        return maps.values().size();
    }


    // nếu price là double
//    public double getAmount(){
//        return maps.values().stream().mapToDouble(item->item.getQuantity()* item.getPrice()).sum();
//    }


    public BigDecimal Summary(){

        BigDecimal totalSum= BigDecimal.ZERO;

        for (CartItem item: maps.values()) {

            BigDecimal itemSum = item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity()));

            System.out.println("item"+ itemSum);

            BigDecimal tien = itemSum.add(totalSum);

            totalSum = tien;




        }

//        System.out.println(totalSum);
        return totalSum;
    }

    public Integer getTotalProduct(){
        Integer Total = 0;
        for (Map.Entry<Long, CartItem> entry : maps.entrySet()) {
            CartItem value = entry.getValue();
            Total += value.getQuantity();
        }
        return Total;

    }

}
