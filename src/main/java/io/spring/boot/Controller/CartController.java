package io.spring.boot.Controller;


import groovy.util.logging.Log;
import io.spring.boot.Entity.*;
import io.spring.boot.Service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/ShopFP")
public class CartController {

    private CategoryService categoryService;

    private CartService cartService;

    private ProductService productService;

    private Order_buyService order_buyService;

    private Order_detailService orderDetailService;

    private CustomerService customerService;

    public CartController(CategoryService categoryService, CartService cartService, ProductService productService, Order_buyService order_buyService, Order_detailService orderDetailService, CustomerService customerService) {
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.productService = productService;
        this.order_buyService = order_buyService;
        this.orderDetailService = orderDetailService;
        this.customerService = customerService;
    }

    @PostMapping("/cart/confirm")
    public String Confirm(HttpSession httpSession,String note,Model model){

        Long cusID = (Long) httpSession.getAttribute("CustomerId");

        Collection<CartItem> cartItems = cartService.getListCart();

        List<Product> productList = productService.fetchStatusDiff0();

        List<Product> errorProduct = new ArrayList<>();

        //check lỗi sản phẩm không tồn tại
        for (CartItem item: cartItems) {
            for (Product pro: productList) {
                if(pro.getId().equals(item.getProduct().getId())){

                    errorProduct.add(item.getProduct());

                }
            }

        }
        System.out.println(errorProduct.size());

        if(errorProduct.size()>0){
            String ErrorMessage = " Có sản phẩm không tồn tại, vui lòng chọn các sản phẩm khác";
            model.addAttribute("ErrorCount", ErrorMessage);
            AddCategory(model);
            Login login = new Login();
            login.checkSession(httpSession,model,"customer");
            return "main/web/Customer/confirmOrder";
        }

        //
        BigDecimal summary = cartService.Summary();

        Customer customer = customerService.findById(cusID);

        Order_buy order_buy = new Order_buy();
        order_buy.setStatus(0);
        order_buy.setCustomer(customer);
        order_buy.setNote(note);
        order_buy.setTotal_price(summary);


        order_buyService.save(order_buy);

        List<Order_buy> orderBuyList = order_buyService.fetchDesTime();

       Order_buy order_buy1 = orderBuyList.get(0);





        for (CartItem cartItem:cartItems) {
            Order_detail orderDetail = new Order_detail();
            orderDetail.setOrder(order_buy1);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getProduct().getPrice());

            orderDetailService.save(orderDetail);
        }




        cartService.clear();

        return "redirect:/ShopFP/support/payment";
    }


    @GetMapping("/cart/confirm")
    public String ConfirmOrder(Model model,HttpSession httpSession){

        Login login = new Login();
        login.checkSession(httpSession,model,"customer");

        AddCategory(model);
        Collection<CartItem> cartItemCollection = cartService.getListCart();

        BigDecimal Summary = cartService.Summary();

        //test
        NumberFormat numberFormat = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.getDefault()));
        String formattedNumber = numberFormat.format(Summary);

        System.out.println(formattedNumber);

        //

        model.addAttribute("cartList",cartItemCollection);

//        model.addAttribute("totalPro",totalPro);

        model.addAttribute("Summary",formattedNumber);

        return "main/web/Customer/confirmOrder";
    }



    @GetMapping(value = "/cart")
    public String cartForm(Model model, HttpSession httpSession){

        Login login = new Login();
        Integer check = login.checkSession(httpSession,model,"customer");

        if(check != 1){
            return "redirect:/ShopFP/login";
        }
        AddCategory(model);

        Collection<CartItem> cartList = cartService.getListCart();



        model.addAttribute("cartList",cartList);


        System.out.println("Danh sách đơn hàng có số sản phẩm là: "+cartList.size());



        return "main/web/Customer/Cart";
    }




    public void AddCategory(Model model){

        List<Category> categoryList0 = categoryService.ListCateParent0();

        List<Category> categoryListExcept0 = categoryService.ListCateParentExcept0();

        model.addAttribute("cateListParent0", categoryList0);
        model.addAttribute("cateListParentExcept0", categoryListExcept0);
    }

}
