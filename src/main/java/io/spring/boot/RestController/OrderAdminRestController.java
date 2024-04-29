package io.spring.boot.RestController;

import io.spring.boot.Entity.*;
import io.spring.boot.Service.CustomerService;
import io.spring.boot.Service.Order_buyService;
import io.spring.boot.Service.Order_detailService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/order")
public class OrderAdminRestController {

    Login login = new Login();
    JsonResponse js = new JsonResponse();
    private Order_buyService order_buyService;

    private Order_detailService orderDetailService;

    public OrderAdminRestController(Order_buyService order_buyService, Order_detailService orderDetailService) {
        this.order_buyService = order_buyService;
        this.orderDetailService = orderDetailService;
    }

    @GetMapping(value = "/list")
    public JsonResponse OrderList(HttpSession httpSession){


        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");


        if(checkLogin==0){
            js.setStatus("error");
            return js;
        }


        return ListOrderDTO();
    }



    @GetMapping("/pending")
    public JsonResponse setPending(@RequestParam String listIdCheck, HttpSession httpSession){

        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");


        if(checkLogin==0){
            js.setStatus("error");
            return js;
        }


        String [] listId = listIdCheck.split(",");

        for (String item: listId) {
            Order_buy order_buy = order_buyService.findById(Long.valueOf(item));
            order_buy.setStatus(1);
            order_buyService.save(order_buy);
        }

        js.setStatus("pending");

        return js;
    }

    @GetMapping("/processing")
    public JsonResponse setProcessing(@RequestParam String listIdCheck, HttpSession httpSession){

        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");


        if(checkLogin==0){
            js.setStatus("error");
            return js;
        }

        String [] listId = listIdCheck.split(",");

        for (String item: listId) {
            Order_buy order_buy = order_buyService.findById(Long.valueOf(item));
            order_buy.setStatus(2);
            order_buyService.save(order_buy);
        }

        js.setStatus("processing");

        return js;
    }

    @GetMapping("/shipped")
    public JsonResponse setShipped(@RequestParam String listIdCheck , HttpSession httpSession){

        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");


        if(checkLogin==0){
            js.setStatus("error");
            return js;
        }

        String [] listId = listIdCheck.split(",");

        for (String item: listId) {
            Order_buy order_buy = order_buyService.findById(Long.valueOf(item));
            order_buy.setStatus(3);
            order_buyService.save(order_buy);
        }

        js.setStatus("shipped");

        return js;
    }

    @GetMapping("/delivered")
    public JsonResponse setDelivered(@RequestParam String listIdCheck, HttpSession httpSession){

        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");


        if(checkLogin==0){
            js.setStatus("error");
            return js;
        }

        String [] listId = listIdCheck.split(",");

        for (String item: listId) {
            Order_buy order_buy = order_buyService.findById(Long.valueOf(item));
            order_buy.setStatus(4);
            order_buyService.save(order_buy);
        }

        js.setStatus("delivered");

        return js;
    }

    @GetMapping("/cancelled")
    public JsonResponse setCancelled(@RequestParam String listIdCheck, HttpSession httpSession){

        Integer checkLogin =  login.checkSessionAPI(httpSession,"admin");


        if(checkLogin==0){
            js.setStatus("error");
            return js;
        }

        String [] listId = listIdCheck.split(",");

        for (String item: listId) {
            Order_buy order_buy = order_buyService.findById(Long.valueOf(item));
            order_buy.setStatus(5);
            order_buyService.save(order_buy);
        }

        js.setStatus("cancelled");

        return js;
    }




    public JsonResponse ListOrderDTO(){


        List<Order_detail> orderDetailsList = new ArrayList<>();

        List<Order_buy> orderBuyList = new ArrayList<>();

        orderDetailsList = orderDetailService.fetchAll();

        System.out.println(orderDetailsList.size());

        orderBuyList = order_buyService.fetchAll();

        // biểu diễn để hiển thị ra list

        List<Product> productList = new ArrayList<>();

        Customer customer = new Customer();

        BigDecimal totalPrice = null;
        String note = null;
        Integer status = null;
        Long id = null;

        for (Order_detail item :orderDetailsList) {
            for (Order_buy order:orderBuyList) {
                if(item.getOrder().getId() == order.getId()){
                    productList.add(item.getProduct());
                    customer = order.getCustomer();
                    totalPrice = order.getTotal_price();
                    note = order.getNote();
                    status = order.getStatus();
                    id = order.getId();
                }
            }
        }





        OrderDTO orderDTO = new OrderDTO(id,customer,productList,totalPrice,note,status);

        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderDTOList.add(orderDTO);

//        Integer totalOrder = orderDTOList.size();

        js.setResult(orderDTOList);
//        js.setResult1(totalOrder);

        System.out.println(orderDTO.getCustomer().getName());

        return js;
    }

}
