package io.spring.boot.Controller;

import io.spring.boot.Entity.*;
import io.spring.boot.Service.CategoryService;
import io.spring.boot.Service.Order_buyService;
import io.spring.boot.Service.Order_detailService;
import io.spring.boot.Service.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin/order")
public class OrderController {

    Login login = new Login();

    private Order_buyService order_buyService;

    private Order_detailService orderDetailService;

    private PaginationService paginationService;

    public OrderController(Order_buyService order_buyService, Order_detailService orderDetailService, PaginationService paginationService) {
        this.order_buyService = order_buyService;
        this.orderDetailService = orderDetailService;
        this.paginationService = paginationService;
    }

    @GetMapping(value = "/list")
    public String home(Model model, HttpSession httpSession){



        return listByPages(1,model,httpSession);
    }


    public List<OrderDTO> ListOrderDTO(){

        List<OrderDTO> orderDTOList = new ArrayList<>();


        List<Order_detail> orderDetailsList = orderDetailService.fetchAll();

        List<Order_buy> orderBuyList = order_buyService.fetchAll();


        // biểu diễn để hiển thị ra list


        Customer customer = new Customer();

        BigDecimal totalPrice = null;
        String note = null;
        Integer status = null;
        Long id = null;


        for (Order_buy order:orderBuyList) {
            List<Product> productList = new ArrayList<>();
            customer = order.getCustomer();
            totalPrice = order.getTotal_price();
            note = order.getNote();
            status = order.getStatus();
            id = order.getId();

            for (Order_detail item :orderDetailsList) {

                    if(item.getOrder().getId().equals(order.getId())){
                        productList.add(item.getProduct());

                    }
                }

            OrderDTO orderDTO = new OrderDTO(id,customer,productList,totalPrice,note,status);
            orderDTOList.add(orderDTO);
        }



        System.out.println("có số đơn hàng là: "+orderDTOList.size());



      return orderDTOList;



    }

    public Page<OrderDTO> getOrderDTO(List<OrderDTO> orderDTOList, Integer pageNumber){


        Pageable pageable = PageRequest.of(pageNumber-1, 5);


        return paginationService.getPage(orderDTOList,pageable);
    }

    @GetMapping("/page/{currentPage}")
    public String listByPages(@PathVariable Integer currentPage, Model model, HttpSession httpSession){


        Integer checkLogin =  login.checkSession(httpSession,model,"admin");



        if(checkLogin==0){
            return "redirect:/admin/login";
        }

        /// Sửa lại


        List<OrderDTO> orderDTOList = ListOrderDTO();

        Integer totalOrder = orderDTOList.size();



        //





        Page<OrderDTO> PageOrderDTO = getOrderDTO(orderDTOList,currentPage);


        Long totalItem = PageOrderDTO.getTotalElements();
        int totalPage = PageOrderDTO.getTotalPages();

        List<Integer> pageTotal = new ArrayList<>();



        for (int i=1;i<=totalPage;i++) {
            pageTotal.add(i);
        }


        List<OrderDTO> ListOrderDTOPage = PageOrderDTO.getContent();




        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalItem",totalItem);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("pageTotal",pageTotal);
        model.addAttribute("totalOrder",totalOrder);

        model.addAttribute("orderDTOList",ListOrderDTOPage);



        return "/main/admin/Order/list";
    }


}

