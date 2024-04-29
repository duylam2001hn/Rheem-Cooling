package io.spring.boot.Controller;

import io.spring.boot.Entity.Admin;
import io.spring.boot.Entity.Customer;
import io.spring.boot.Entity.Login;
import io.spring.boot.Entity.Product;
import io.spring.boot.Service.CustomerService;
import io.spring.boot.Service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/customer")
public class CustomerController {

    Login login = new Login();
    List<Customer> customerList = new ArrayList<>();
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/list")
    public String CustomerHome(Model model, HttpSession httpSession){


        return listByPages(1,model,httpSession);
    }


    @GetMapping("/page/{currentPage}")
    public String listByPages(@PathVariable Integer currentPage, Model model,HttpSession httpSession){

        Integer checkLogin =  login.checkSession(httpSession,model,"admin");



        if(checkLogin==0){
            return "redirect:/admin/login";
        }


        Page<Customer> PageCate = customerService.getUsersSortedByLastNameAndPaged(currentPage);

        Long totalItem = PageCate.getTotalElements();
        int totalPage = PageCate.getTotalPages();
        List<Integer> pageTotal = new ArrayList<>();



        for (int i=1;i<=totalPage;i++) {
            pageTotal.add(i);
        }


        customerList = PageCate.getContent();


        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalItem",totalItem);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("pageTotal",pageTotal);


        model.addAttribute("customerList",customerList);



        return "/main/admin/Customer/list";
    }
}
