package io.spring.boot.RestController;

import io.spring.boot.Entity.CartItem;
import io.spring.boot.Entity.JsonResponse;
import io.spring.boot.Entity.Product;
import io.spring.boot.Service.CartService;
import io.spring.boot.Service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping(value = "/api/cart")
public class CartRestController {

    Integer TotalSp = 0;
    private CartService cartService;

    private ProductService productService;

    public CartRestController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping(value = "/add")
    public JsonResponse addItemToCart(@RequestParam Long productId, @RequestParam int quantity){

        JsonResponse js = new JsonResponse();



        Product product = productService.findById(productId);


        // Create a new cart item
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(product.getPrice());

        // Add the cart item to the cart
        cartService.addItemToCart(cartItem);


        TotalSp = cartService.getTotalProduct();
//



        js.setStatus("success");
        js.setResult(TotalSp);
        return js;
    }

    @GetMapping("/TotalSp")
    public JsonResponse getTotalSp(){
        JsonResponse js = new JsonResponse();
        TotalSp = cartService.getTotalProduct();
        js.setResult(TotalSp);
        return js;
    }

    @GetMapping("/delete/{id}")
    public JsonResponse deleteItem(@PathVariable Long id){
        JsonResponse js = new JsonResponse();


        cartService.remove(id);

        js.setStatus("delete");

        return js;
    }

    @GetMapping ("/update")
    public JsonResponse updateCart(@RequestParam String productId,@RequestParam String Quantity){
        JsonResponse js = new JsonResponse();


        String [] productList = productId.split(",");

        String [] QuantityList = Quantity.split(",");


        for(int i =0;i<productList.length;i++){
            String proId = productList[i];
            String quantity = QuantityList[i];

            cartService.update(Long.valueOf(proId),Integer.valueOf(quantity));
        }






        js.setStatus("update");
        return js;
    }

    @GetMapping("/clear")
    public JsonResponse clearCart(){
        JsonResponse jsonResponse = new JsonResponse();


        cartService.clear();
        jsonResponse.setStatus("clear");
        return jsonResponse;
    }


}
